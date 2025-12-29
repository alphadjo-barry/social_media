package com.alphadjo.social_media.service.contract;

import com.alphadjo.social_media.dto.validation.MailDto;

public interface SendMailService {

    void sendMail(MailDto mailDto);
}
