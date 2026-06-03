package br.com.fiap.firesearchlogic.messaging;

import br.com.fiap.firesearchlogic.service.IotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class IotConsumer {

    private final IotService iotService;

    @RabbitListener(queues = "${rabbitmq.queue.iot}")
    public void consumir(Map<String, Object> payload) {
        log.info("[IOT-CONSUMER] Mensagem recebida da fila IoT");
        iotService.processarLeituraAssincrona(payload);
    }
}