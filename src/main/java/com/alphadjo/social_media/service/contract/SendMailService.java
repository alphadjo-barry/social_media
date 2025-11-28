package com.alphadjo.social_media.service.contract;

import com.alphadjo.social_media.dto.validation.MessageRabbitDto;

public interface SendMailService {

    void sendMail(MessageRabbitDto messageRabbitDto);
}
