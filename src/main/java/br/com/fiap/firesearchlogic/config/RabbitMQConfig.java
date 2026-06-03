package br.com.fiap.firesearchlogic.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.firms}")
    private String queueFirms;

    @Value("${rabbitmq.queue.iot}")
    private String queueIot;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing-key.firms}")
    private String routingKeyFirms;

    @Value("${rabbitmq.routing-key.iot}")
    private String routingKeyIot;

    @Bean
    public Queue queueFirms() {
        return QueueBuilder.durable(queueFirms).build();
    }

    @Bean
    public Queue queueIot() {
        return QueueBuilder.durable(queueIot).build();
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding bindingFirms(Queue queueFirms, TopicExchange exchange) {
        return BindingBuilder.bind(queueFirms).to(exchange).with(routingKeyFirms);
    }

    @Bean
    public Binding bindingIot(Queue queueIot, TopicExchange exchange) {
        return BindingBuilder.bind(queueIot).to(exchange).with(routingKeyIot);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

}
