package br.com.fiap.firesearchlogic.controller;

import br.com.fiap.firesearchlogic.dto.HistoricoRiscoResponseDTO;
import br.com.fiap.firesearchlogic.repository.HistoricoRiscoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historico-risco")
@RequiredArgsConstructor
@Tag(name = "Histórico de Risco", description = "Histórico de scores calculados pela firesearchIA")
public class HistoricoRiscoController {

    private final HistoricoRiscoRepository historicoRiscoRepository;

    @GetMapping
    @Operation(summary = "Listar todo o histórico de risco")
    public List<HistoricoRiscoResponseDTO> listar() {
        return historicoRiscoRepository.findAll().stream()
                .map(h -> HistoricoRiscoResponseDTO.builder()
                        .id(h.getId())
                        .score(h.getScore())
                        .dsNivel(h.getDsNivel())
                        .dataCalculo(h.getDataCalculo())
                        .regiaoId(h.getRegiao().getId())
                        .regiaoNome(h.getRegiao().getNmRegiao())
                        .build())
                .toList();
    }

    @GetMapping("/alto-risco")
    @Operation(summary = "Listar regiões com score acima de 70")
    public List<HistoricoRiscoResponseDTO> altoRisco() {
        return historicoRiscoRepository.findByScoreGreaterThanOrderByDataCalculoDesc(70.0).stream()
                .map(h -> HistoricoRiscoResponseDTO.builder()
                        .id(h.getId())
                        .score(h.getScore())
                        .dsNivel(h.getDsNivel())
                        .dataCalculo(h.getDataCalculo())
                        .regiaoId(h.getRegiao().getId())
                        .regiaoNome(h.getRegiao().getNmRegiao())
                        .build())
                .toList();
    }
}