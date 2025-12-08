package com.alphadjo.social_media.rabbit;

import com.alphadjo.social_media.dto.validation.MessageRabbitDto;

import com.alphadjo.social_media.service.contract.SendMailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class MailConsumer {

    private final SendMailService  sendMailService;

    @RabbitListener(queues = "mail.queue")
    public void consume(MessageRabbitDto message) {
        log.info("Received a message from mail queue");
        sendMailService.sendMail(message);
    }
}
