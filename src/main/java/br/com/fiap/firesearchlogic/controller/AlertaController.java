package br.com.fiap.firesearchlogic.controller;

import br.com.fiap.firesearchlogic.dto.AlertaResponseDTO;
import br.com.fiap.firesearchlogic.repository.AlertaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
@RequiredArgsConstructor
@Tag(name = "Alertas", description = "Consulta de alertas gerados")
public class AlertaController {

    private final AlertaRepository alertaRepository;

    @GetMapping
    @Operation(summary = "Listar todos os alertas")
    public List<AlertaResponseDTO> listar() {
        return alertaRepository.findAllByOrderByDataEmissaoDesc().stream()
                .map(a -> AlertaResponseDTO.builder()
                        .id(a.getId())
                        .dsNivel(a.getDsNivel())
                        .dsMensagem(a.getDsMensagem())
                        .dataEmissao(a.getDataEmissao())
                        .flNotificado(a.getFlNotificado())
                        .regiaoId(a.getHistoricoRisco().getRegiao().getId())
                        .regiaoNome(a.getHistoricoRisco().getRegiao().getNmRegiao())
                        .build())
                .toList();
    }

    @GetMapping("/nivel/{nivel}")
    @Operation(summary = "Filtrar alertas por nível (ALTO, MEDIO, BAIXO)")
    public List<AlertaResponseDTO> porNivel(@PathVariable String nivel) {
        return alertaRepository.findByDsNivelOrderByDataEmissaoDesc(nivel.toUpperCase()).stream()
                .map(a -> AlertaResponseDTO.builder()
                        .id(a.getId())
                        .dsNivel(a.getDsNivel())
                        .dsMensagem(a.getDsMensagem())
                        .dataEmissao(a.getDataEmissao())
                        .flNotificado(a.getFlNotificado())
                        .regiaoId(a.getHistoricoRisco().getRegiao().getId())
                        .regiaoNome(a.getHistoricoRisco().getRegiao().getNmRegiao())
                        .build())
                .toList();
    }
}