package br.com.fiap.firesearchlogic.service;

import br.com.fiap.firesearchlogic.entity.Alerta;
import br.com.fiap.firesearchlogic.entity.HistoricoRisco;
import br.com.fiap.firesearchlogic.repository.AlertaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlertaService {

    private final AlertaRepository alertaRepository;

    public void gerarAlerta(HistoricoRisco historico) {
        String nivel = resolverNivel(historico.getScore());
        String mensagem = String.format(
                "Risco %s detectado na região %s. Score: %.1f",
                nivel, historico.getRegiao().getNmRegiao(), historico.getScore()
        );

        Alerta alerta = Alerta.builder()
                .historicoRisco(historico)
                .dsNivel(nivel)
                .dsMensagem(mensagem)
                .dataEmissao(LocalDateTime.now())
                .flNotificado("N")
                .build();

        alertaRepository.save(alerta);
        log.info("[ALERTA] Gerado alerta {} para região {}", nivel, historico.getRegiao().getNmRegiao());
    }

    private String resolverNivel(Double score) {
        if (score >= 70) return "ALTO";
        if (score >= 40) return "MEDIO";
        return "BAIXO";
    }
}