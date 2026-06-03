package br.com.fiap.firesearchlogic.service;

import br.com.fiap.firesearchlogic.entity.Regiao;
import br.com.fiap.firesearchlogic.repository.RegiaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class IotService {

    private final RegiaoRepository regiaoRepository;
    private final RiscoService riscoService;
    private final RestTemplate restTemplate;

    @Value("${firesearch.ia.url}")
    private String firesearchIaUrl;

    public void processarLeituraAssincrona(Map<String, Object> payload) {
        try {
            Double lat = Double.parseDouble(payload.get("latitude").toString());
            Double lon = Double.parseDouble(payload.get("longitude").toString());
            Integer raio = payload.containsKey("raioKm")
                    ? Integer.parseInt(payload.get("raioKm").toString()) : 50;

            List<Regiao> regioes = regiaoRepository.findRegioesDentroDoRaio(lat, lon, raio);

            if (regioes.isEmpty()) {
                log.info("[IOT] Nenhuma região monitorada próxima ao sensor lat={} lon={}", lat, lon);
                return;
            }

            // Chama firesearchIA
            Map<String, Object> resposta = restTemplate.postForObject(
                    firesearchIaUrl + "/predict", payload, Map.class);

            if (resposta == null) {
                log.warn("[IOT] firesearchIA retornou resposta nula");
                return;
            }

            Double score = Double.parseDouble(resposta.get("score").toString());

            for (Regiao regiao : regioes) {
                riscoService.salvarRisco(regiao, score);
            }

        } catch (Exception e) {
            log.error("[IOT] Erro ao processar leitura: {}", e.getMessage());
        }
    }
}