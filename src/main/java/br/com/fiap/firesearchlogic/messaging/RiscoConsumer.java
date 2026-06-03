package br.com.fiap.firesearchlogic.messaging;

import br.com.fiap.firesearchlogic.entity.FocoCalor;
import br.com.fiap.firesearchlogic.entity.Regiao;
import br.com.fiap.firesearchlogic.repository.FocoCalorRepository;
import br.com.fiap.firesearchlogic.repository.RegiaoRepository;
import br.com.fiap.firesearchlogic.service.RiscoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class RiscoConsumer {

    private final FocoCalorRepository focoCalorRepository;
    private final RegiaoRepository regiaoRepository;
    private final RiscoService riscoService;
    private final RestTemplate restTemplate;

    @Value("${firesearch.ia.url}")
    private String firesearchIaUrl;

    @RabbitListener(queues = "${rabbitmq.queue.firms}")
    public void consumir(Long focoId) {
        try {
            FocoCalor foco = focoCalorRepository.findById(focoId).orElse(null);
            if (foco == null) {
                log.warn("[RISCO-CONSUMER] FocoCalor id={} não encontrado", focoId);
                return;
            }

            List<Regiao> regioes = regiaoRepository.findRegioesDentroDoRaio(
                    foco.getFcLatitude(), foco.getFcLongitude(), 100);

            if (regioes.isEmpty()) {
                log.info("[RISCO-CONSUMER] Nenhuma região próxima ao foco id={}", focoId);
                return;
            }

            Map<String, Object> payload = Map.of(
                    "brightness", foco.getBrightness(),
                    "frp", foco.getFrp(),
                    "confidence", foco.getConfidence()
            );

            Map<String, Object> resposta = restTemplate.postForObject(
                    firesearchIaUrl + "/predict", payload, Map.class);

            if (resposta == null) return;

            Double score = Double.parseDouble(resposta.get("score").toString());

            for (Regiao regiao : regioes) {
                riscoService.salvarRisco(regiao, score);
            }

        } catch (Exception e) {
            log.error("[RISCO-CONSUMER] Erro ao processar foco id={}: {}", focoId, e.getMessage());
        }
    }
}