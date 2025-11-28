package com.alphadjo.social_media.service.impl;

import com.alphadjo.social_media.dto.password.PasswordRequest;
import com.alphadjo.social_media.dto.utilisateur.UtilisateurDto;
import com.alphadjo.social_media.dto.validation.ValidationRequest;

import com.alphadjo.social_media.entity.Role;
import com.alphadjo.social_media.entity.Utilisateur;
import com.alphadjo.social_media.entity.Validation;
import com.alphadjo.social_media.enums.RoleEnum;
import com.alphadjo.social_media.repository.contract.RoleRepository;
import com.alphadjo.social_media.repository.contract.UtilisateurRepository;

import com.alphadjo.social_media.service.contract.AuthenticationUserService;
import com.alphadjo.social_media.service.contract.UtilisateurService;
import com.alphadjo.social_media.service.contract.ValidationService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {

    private final ValidationService validationService;
    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationUserService authenticationUserService;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, readOnly = true)
    public List<UtilisateurDto> findAll() {
        return utilisateurRepository.findAll().stream()
                .map(UtilisateurDto::fromEntity)
                .toList();
    }

    @Override
    public UtilisateurDto findById(Long id) {

        Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Utilisateur not found !"));

        return UtilisateurDto.fromEntity(utilisateur) ;
    }

    @Override
    public UtilisateurDto save(UtilisateurDto dto) {
        return saveUserWithRole(dto, RoleEnum.USER);
    }

    @Override
    public UtilisateurDto update(UtilisateurDto dto, Long id) {

        return null;
    }

    @Override
    public void delete(Long id) {

        Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Utilisateur not found !"));

        utilisateurRepository.delete(utilisateur);
    }

    @Override
    public UtilisateurDto saveAdmin(UtilisateurDto dto) {
        return saveUserWithRole(dto, RoleEnum.ADMIN);
    }

    @Override
    public String enableAccount(ValidationRequest request) {

        Validation validation = validationService.findByCode(request.code());

        if(validation.getExpiredAt().isBefore(Instant.now())){
            throw new IllegalArgumentException("Validation code is expired");
        }

        Utilisateur utilisateur = validation.getUtilisateur();

        if(utilisateur.isActive()) throw new IllegalArgumentException("Account is already enabled");

        utilisateur.setActive(true);

        validationService.setValidatedAt(validation);
        utilisateurRepository.save(utilisateur);

        return "Your account has been enabled, you can now login";
    }

    @Override
    public boolean enableById(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Utilisateur not found !"));

        if(utilisateur.isEnabled()){
            throw new IllegalArgumentException("Utilisateur is already disabled");
        }

        utilisateur.setActive(true);
        return this.utilisateurRepository.save(utilisateur).isActive();
    }

    @Override
    public boolean disableById(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Utilisateur not found !"));

        if(!utilisateur.isEnabled()){
            throw new IllegalArgumentException("Utilisateur is already disabled");
        }

        utilisateur.setActive(false);
        return this.utilisateurRepository.save(utilisateur).isActive();
    }

    @Override
    public String passwordChange(Long id, PasswordRequest request) {

        Jwt jwt = authenticationUserService.getAuthenticatedUser();

        Utilisateur utilisateur = utilisateurRepository.findByEmail(jwt.getSubject())
                .orElseThrow(() -> new EntityNotFoundException("No one logged in user in the system !"));

        if (!bCryptPasswordEncoder.matches(request.oldPassword(), utilisateur.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        if (request.newPassword().length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }

        utilisateur.setPassword(bCryptPasswordEncoder.encode(request.newPassword()));
        utilisateurRepository.save(utilisateur);

        return "Password has been changed successfully";
    }

    private UtilisateurDto saveUserWithRole(UtilisateurDto dto, RoleEnum roleEnum) {
        Utilisateur utilisateur = UtilisateurDto.toEntity(dto);

        if (utilisateurRepository.findByEmail(utilisateur.getEmail()).isPresent()) {
            throw new EntityExistsException("Email already exists in the system, please try another email address");
        }

        Role role = roleRepository.findByName(roleEnum.name())
                .orElseGet(() -> roleRepository.save(Role.builder().name(roleEnum.name()).build()));

        utilisateur.setRoles(Set.of(role));
        utilisateur.setPassword(bCryptPasswordEncoder.encode(utilisateur.getPassword()));

        Utilisateur savedUser = utilisateurRepository.save(utilisateur);
        validationService.saveValidation(utilisateur);

        return UtilisateurDto.fromEntity(savedUser);
    }
}
