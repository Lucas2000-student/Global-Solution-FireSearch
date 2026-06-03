package br.com.fiap.firesearchlogic.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class HistoricoRiscoResponseDTO {
    private Long id;
    private Double score;
    private String dsNivel;
    private LocalDate dataCalculo;
    private Long regiaoId;
    private String regiaoNome;
}