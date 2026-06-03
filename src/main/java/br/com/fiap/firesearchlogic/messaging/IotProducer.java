package br.com.fiap.firesearchlogic.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class IotProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing-key.iot}")
    private String routingKey;

    public void publicarLeitura(Map<String, Object> payload) {
        log.info("[IOT] Publicando leitura de sensor na fila");
        rabbitTemplate.convertAndSend(exchange, routingKey, payload);
    }
}