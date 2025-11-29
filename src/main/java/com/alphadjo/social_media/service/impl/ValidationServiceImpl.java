package com.alphadjo.social_media.service.impl;

import com.alphadjo.social_media.entity.Utilisateur;
import com.alphadjo.social_media.entity.Validation;
import com.alphadjo.social_media.rabbit.MailProducer;
import com.alphadjo.social_media.repository.contract.ValidationRepository;

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
    private final MailProducer mailProducer;

    @Override
    public void saveValidation(Utilisateur utilisateur) {

        Validation validation = new Validation();
        validation.setCreatedAt(Instant.now());
        validation.setExpiredAt(Instant.now().plus(60 * 5, ChronoUnit.SECONDS));

        Random random = new Random();
        int code = random.nextInt(999999);
        String codeInString = String.format("%06d", code);
        validation.setCode(codeInString);
        validation.setUtilisateur(utilisateur);

        validationRepository.save(validation);
        mailProducer.sendValidation(validation);
    }

    @Override
    public Validation findByCode(String code) {

       return validationRepository.findByCode(code)
               .orElseThrow(() -> new EntityNotFoundException("Validation code is incorrect"));
    }

    @Override
    public void setValidatedAt(Validation validation) {
        validation.setValidatedAt(Instant.now());
        validation.setValidated(true);
        validationRepository.save(validation);
    }
}
