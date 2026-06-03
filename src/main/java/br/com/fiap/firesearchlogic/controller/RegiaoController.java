package br.com.fiap.firesearchlogic.controller;

import br.com.fiap.firesearchlogic.dto.RegiaoRequestDTO;
import br.com.fiap.firesearchlogic.dto.RegiaoResponseDTO;
import br.com.fiap.firesearchlogic.entity.Regiao;
import br.com.fiap.firesearchlogic.repository.RegiaoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regioes")
@RequiredArgsConstructor
@Tag(name = "Regiões", description = "Gerenciamento de regiões monitoradas")
public class RegiaoController {

    private final RegiaoRepository regiaoRepository;

    @GetMapping
    @Operation(summary = "Listar todas as regiões")
    public List<RegiaoResponseDTO> listar() {
        return regiaoRepository.findAll().stream()
                .map(this::toDTO).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar região por ID")
    public ResponseEntity<RegiaoResponseDTO> buscar(@PathVariable Long id) {
        return regiaoRepository.findById(id)
                .map(r -> ResponseEntity.ok(toDTO(r)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cadastrar nova região")
    public ResponseEntity<RegiaoResponseDTO> criar(@Valid @RequestBody RegiaoRequestDTO dto) {
        Regiao regiao = Regiao.builder()
                .nmRegiao(dto.getNmRegiao())
                .nmEstado(dto.getNmEstado())
                .nmPais(dto.getNmPais())
                .reLatitude(dto.getReLatitude())
                .reLongitude(dto.getReLongitude())
                .build();
        regiao = regiaoRepository.save(regiao);
        return ResponseEntity.ok(toDTO(regiao));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover região")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!regiaoRepository.existsById(id)) return ResponseEntity.notFound().build();
        regiaoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private RegiaoResponseDTO toDTO(Regiao r) {
        return RegiaoResponseDTO.builder()
                .id(r.getId())
                .nmRegiao(r.getNmRegiao())
                .nmEstado(r.getNmEstado())
                .nmPais(r.getNmPais())
                .reLatitude(r.getReLatitude())
                .reLongitude(r.getReLongitude())
                .build();
    }
}