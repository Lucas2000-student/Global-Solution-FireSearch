package br.com.fiap.firesearchlogic.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class AlertaResponseDTO {
    private Long id;
    private String dsNivel;
    private String dsMensagem;
    private LocalDateTime dataEmissao;
    private String flNotificado;
    private Long regiaoId;
    private String regiaoNome;
}