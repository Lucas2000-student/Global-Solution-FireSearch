package br.com.fiap.firesearchlogic.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IotSensorDTO {

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @NotNull
    private Double temperatura;

    @NotNull
    private Double umidade;

    @NotNull
    private Double co2;

    @NotNull
    private Double fumaca;

    private Integer raioKm = 50;
}