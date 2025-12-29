package com.alphadjo.social_media.kafka;

import com.alphadjo.social_media.dto.validation.MailDto;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmailProducerImpl implements EmailProducer {
    private final KafkaTemplate<String, MailDto> kafkaTemplate;

    public void sendMail(String  topic, MailDto mailDto){
        kafkaTemplate.send(topic, mailDto);
    }
}
