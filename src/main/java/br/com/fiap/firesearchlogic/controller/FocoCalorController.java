package br.com.fiap.firesearchlogic.controller;

import br.com.fiap.firesearchlogic.dto.FocoCalorResponseDTO;
import br.com.fiap.firesearchlogic.repository.FocoCalorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/focos")
@RequiredArgsConstructor
@Tag(name = "Focos de Calor", description = "Dados de focos importados da NASA FIRMS")
public class FocoCalorController {

    private final FocoCalorRepository focoCalorRepository;

    @GetMapping
    @Operation(summary = "Listar todos os focos de calor")
    public List<FocoCalorResponseDTO> listar() {
        return focoCalorRepository.findAll().stream()
                .map(f -> FocoCalorResponseDTO.builder()
                        .id(f.getId())
                        .fcLatitude(f.getFcLatitude())
                        .fcLongitude(f.getFcLongitude())
                        .brightness(f.getBrightness())
                        .frp(f.getFrp())
                        .confidence(f.getConfidence())
                        .dtAquisicao(f.getDtAquisicao())
                        .nmSateliteOrigem(f.getNmSateliteOrigem())
                        .regiaoId(f.getRegiao().getId())
                        .regiaoNome(f.getRegiao().getNmRegiao())
                        .build())
                .toList();
    }
}