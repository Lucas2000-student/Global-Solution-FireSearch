package br.com.fiap.firesearchlogic.service;

import br.com.fiap.firesearchlogic.entity.HistoricoRisco;
import br.com.fiap.firesearchlogic.entity.Regiao;
import br.com.fiap.firesearchlogic.repository.HistoricoRiscoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiscoService {

    private final HistoricoRiscoRepository historicoRiscoRepository;
    private final AlertaService alertaService;

    public HistoricoRisco salvarRisco(Regiao regiao, Double score) {
        String nivel = resolverNivel(score);

        HistoricoRisco historico = HistoricoRisco.builder()
                .regiao(regiao)
                .score(score)
                .dsNivel(nivel)
                .dataCalculo(LocalDate.now())
                .build();

        historico = historicoRiscoRepository.save(historico);
        log.info("[RISCO] Score={} nivel={} salvo para região {}", score, nivel, regiao.getNmRegiao());

        if (score >= 70) {
            alertaService.gerarAlerta(historico);
        }

        return historico;
    }

    private String resolverNivel(Double score) {
        if (score >= 70) return "ALTO";
        if (score >= 40) return "MEDIO";
        return "BAIXO";
    }
}