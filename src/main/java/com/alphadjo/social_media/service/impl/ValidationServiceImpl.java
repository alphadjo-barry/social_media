package com.alphadjo.social_media.service.impl;

import com.alphadjo.social_media.dto.validation.MailDto;
import com.alphadjo.social_media.entity.Utilisateur;
import com.alphadjo.social_media.entity.Validation;
import com.alphadjo.social_media.kafka.EmailProducer;
import com.alphadjo.social_media.repository.contract.ValidationRepository;

import com.alphadjo.social_media.service.contract.ValidationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.Random;

@Service
@AllArgsConstructor
public class ValidationServiceImpl implements ValidationService {

    private final ValidationRepository validationRepository;
    private final EmailProducer emailProducer;
    public static final String MAIL_TOPIC = "mail-topic";

    private final KafkaTemplate<String,MailDto> kafkaTemplate;

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
        MailDto mailDto = new MailDto(
                validation.getCode(),
                validation.getUtilisateur().getEmail(),
                validation.getUtilisateur().getFirstName(),
                validation.getUtilisateur().getLastName());
        emailProducer.sendMail(MAIL_TOPIC, mailDto);
    }

    @Override
    public Validation findByCode(String code) {

       return validationRepository.findByCode(code)
               .orElseThrow(() -> new EntityNotFoundException("validation code is incorrect or not found"));
    }

    @Override
    public void setValidatedAt(Validation validation) {
        validation.setValidatedAt(Instant.now());
        validation.setValidated(true);
        validationRepository.save(validation);
    }

    @Scheduled(cron = "* */5 * * * *")
    public void deleteExpiredValidation(){
        validationRepository.deleteExpired(Instant.now().minus(5, ChronoUnit.MINUTES));
    }
}
