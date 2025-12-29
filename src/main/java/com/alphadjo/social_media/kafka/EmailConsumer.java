package com.alphadjo.social_media.kafka;

import com.alphadjo.social_media.dto.validation.MailDto;

import com.alphadjo.social_media.service.contract.SendMailService;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class EmailConsumer {
    private  final SendMailService sendMailService;

    @KafkaListener(topics = "mail-topic", groupId = "mail-group")
    public void listen(MailDto mail){
        log.info("Received a message from mail topic");
        this.sendMailService.sendMail(mail);
    }
}
