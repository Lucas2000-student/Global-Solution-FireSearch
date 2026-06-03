package br.com.fiap.firesearchlogic.messaging;

import br.com.fiap.firesearchlogic.entity.FocoCalor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FirmsProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing-key.firms}")
    private String routingKey;

    public void publicarFoco(FocoCalor foco) {
        log.info("[FIRMS] Publicando foco id={} na fila", foco.getId());
        rabbitTemplate.convertAndSend(exchange, routingKey, foco.getId());
    }
}