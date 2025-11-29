package com.alphadjo.social_media.rabbit;

import com.alphadjo.social_media.configuration.RabbitConfig;
import com.alphadjo.social_media.dto.validation.MessageRabbitDto;
import com.alphadjo.social_media.entity.Validation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class MailProducer implements MailProducerInterface {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendValidation(Validation validation) {

        MessageRabbitDto messageRabbitDto = new MessageRabbitDto(
                validation.getCode(),
                validation.getUtilisateur().getEmail(),
                validation.getUtilisateur().getFirstName(),
                validation.getUtilisateur().getLastName());

        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE_NAME,
                RabbitConfig.MAIL_ROUTING_KEY,
                messageRabbitDto);

        log.info("Message envoyé à RabbitMQ : {}", validation.getCode());
    }

}
