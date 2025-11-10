package com.alphadjo.social_media.service.impl;

import com.alphadjo.social_media.entity.Utilisateur;
import com.alphadjo.social_media.entity.Validation;
import com.alphadjo.social_media.repository.contract.ValidationRepository;
import com.alphadjo.social_media.service.contract.SendMailService;
import com.alphadjo.social_media.service.contract.ValidationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.Random;

@Service
@AllArgsConstructor
public class ValidationServiceImpl implements ValidationService {

    private final ValidationRepository validationRepository;
    private final SendMailService sendMailService;

    @Override
    public void saveValidation(Utilisateur utilisateur) {

        Validation validation = new Validation();
        validation.setCreatedAt(Instant.now());
        validation.setExpiredAt(Instant.now().plus(60 * 15, ChronoUnit.SECONDS));

        Random random = new Random();
        int code = random.nextInt(999999);
        String codeInString = String.format("%06d", code);
        validation.setCode(codeInString);
        validation.setUtilisateur(utilisateur);

        validationRepository.save(validation);
        sendMailService.sendMail(validation);
    }

    @Override
    public Validation findByCode(String code) {

        Validation validation = validationRepository.findByCode(code).orElseThrow(
                () -> new EntityNotFoundException("Validation code is incorrect")
        );

        this.setValidatedAt(validation);
        return validation;
    }

    @Override
    public void setValidatedAt(Validation validation) {
        validation.setValidatedAt(Instant.now());
    }
}
