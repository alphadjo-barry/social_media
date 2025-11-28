package com.alphadjo.social_media.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    public static final String QUEUE_NAME = "mail.queue";
    public static final String EXCHANGE_NAME = "mail.exchange";
    public static final String MAIL_ROUTING_KEY = "mail.send";

    @Bean
    public Queue mailQueue() {
        return new Queue(QUEUE_NAME,  true);
    }

    @Bean
    public DirectExchange mailExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding mailBinding(Queue mailQueue, Exchange mailExchange) {
        return BindingBuilder.bind(mailQueue)
                .to(mailExchange)
                .with(MAIL_ROUTING_KEY).noargs();
    }

    @Bean
    public MessageConverter mailMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(mailMessageConverter());
        return template;
    }
}
