package br.com.fiap.firesearchlogic.controller;

import br.com.fiap.firesearchlogic.dto.IotSensorDTO;
import br.com.fiap.firesearchlogic.messaging.IotProducer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/iot")
@RequiredArgsConstructor
@Tag(name = "IoT", description = "Recebimento de leituras de sensores IoT")
public class IotController {

    private final IotProducer iotProducer;

    @PostMapping("/sensor")
    @Operation(summary = "Receber leitura de sensor IoT e publicar na fila")
    public ResponseEntity<Map<String, String>> receberLeitura(@Valid @RequestBody IotSensorDTO dto) {
        Map<String, Object> payload = Map.of(
                "latitude", dto.getLatitude(),
                "longitude", dto.getLongitude(),
                "temperatura", dto.getTemperatura(),
                "umidade", dto.getUmidade(),
                "co2", dto.getCo2(),
                "fumaca", dto.getFumaca(),
                "raioKm", dto.getRaioKm()
        );
        iotProducer.publicarLeitura(payload);
        return ResponseEntity.accepted()
                .body(Map.of("status", "Leitura recebida e enfileirada para processamento"));
    }
}