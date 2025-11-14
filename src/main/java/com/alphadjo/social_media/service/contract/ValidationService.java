package com.alphadjo.social_media.service.contract;

import com.alphadjo.social_media.dto.validation.ValidationRequest;
import com.alphadjo.social_media.entity.Utilisateur;
import com.alphadjo.social_media.entity.Validation;

public interface ValidationService {

    void saveValidation(Utilisateur utilisateur);
    Validation findByCode(String code);
    void setValidatedAt(Validation validation);
}
