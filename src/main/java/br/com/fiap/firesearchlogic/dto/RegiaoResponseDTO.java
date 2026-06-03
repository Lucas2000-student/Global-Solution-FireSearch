package br.com.fiap.firesearchlogic.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegiaoResponseDTO {
    private Long id;
    private String nmRegiao;
    private String nmEstado;
    private String nmPais;
    private Double reLatitude;
    private Double reLongitude;
}