package com.alphadjo.social_media.kafka;

import com.alphadjo.social_media.dto.validation.MailDto;

public interface EmailProducer {
    void sendMail(String  topic, MailDto mailDto);
}
