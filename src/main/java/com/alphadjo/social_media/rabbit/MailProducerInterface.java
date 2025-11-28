package com.alphadjo.social_media.rabbit;

import com.alphadjo.social_media.entity.Validation;

public interface MailProducerInterface {

    public void sendValidation(Validation validation);
}
