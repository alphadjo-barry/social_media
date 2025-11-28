package com.alphadjo.social_media.rabbit;

import com.alphadjo.social_media.dto.validation.MessageRabbitDto;

import com.alphadjo.social_media.service.contract.SendMailService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MailConsumer {

    private final SendMailService  sendMailService;

    @RabbitListener(queues = "mail.queue")
    public void consume(MessageRabbitDto message) {
        sendMailService.sendMail(message);
    }
}
