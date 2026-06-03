package br.com.fiap.firesearchlogic.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class FocoCalorResponseDTO {
    private Long id;
    private Double fcLatitude;
    private Double fcLongitude;
    private Double brightness;
    private Double frp;
    private String confidence;
    private LocalDate dtAquisicao;
    private String nmSateliteOrigem;
    private Long regiaoId;
    private String regiaoNome;
}