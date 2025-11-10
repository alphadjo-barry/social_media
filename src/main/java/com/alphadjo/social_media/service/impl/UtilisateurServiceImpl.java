package com.alphadjo.social_media.service.impl;

import com.alphadjo.social_media.dto.utilisateur.UtilisateurDto;
import com.alphadjo.social_media.entity.Role;
import com.alphadjo.social_media.entity.Utilisateur;
import com.alphadjo.social_media.enums.RoleEnum;
import com.alphadjo.social_media.repository.contract.RoleRepository;
import com.alphadjo.social_media.repository.contract.UtilisationRepository;
import com.alphadjo.social_media.service.contract.MinioService;
import com.alphadjo.social_media.service.contract.UtilisateurService;
import com.alphadjo.social_media.service.contract.ValidationService;
import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {

    private final ValidationService validationService;
    private final UtilisationRepository utilisationRepository;
    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<UtilisateurDto> findAll() {
        return List.of();
    }

    @Override
    public UtilisateurDto findById(Long id) {
        return null;
    }

    @Override
    public UtilisateurDto save(UtilisateurDto dto) {

        Utilisateur utilisateur = UtilisateurDto.toEntity(dto);

        if(utilisationRepository.findByEmail(utilisateur.getEmail()).isPresent()){
            throw new EntityExistsException("Email already exists in the system, please try another email address");
        }

        Role role = roleRepository.findByName(RoleEnum.USER.name()).orElseGet(
                () -> roleRepository.save(Role.builder().name(RoleEnum.USER.name()).build()));

        utilisateur.setRoles(Set.of(role));
        utilisateur.setPassword(bCryptPasswordEncoder.encode(utilisateur.getPassword()));

        Utilisateur savedUser = utilisationRepository.save(utilisateur);
        validationService.saveValidation(utilisateur);

        return UtilisateurDto.fromEntity(utilisationRepository.save(savedUser));
    }

    @Override
    public UtilisateurDto update(UtilisateurDto dto, Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Jwt getAuthenticatedUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof Jwt){
            return (Jwt) authentication.getPrincipal() ;
        }

        return null;
    }

}
