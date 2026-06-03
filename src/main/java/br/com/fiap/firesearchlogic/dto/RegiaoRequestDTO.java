package br.com.fiap.firesearchlogic.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegiaoRequestDTO {

    @NotBlank
    private String nmRegiao;

    @NotBlank
    private String nmEstado;

    @NotBlank
    private String nmPais;

    @NotNull
    @DecimalMin("-90.0") @DecimalMax("90.0")
    private Double reLatitude;

    @NotNull
    @DecimalMin("-180.0") @DecimalMax("180.0")
    private Double reLongitude;
}