package com.alphadjo.social_media.service.contract;

import com.alphadjo.social_media.dto.utilisateur.UtilisateurDto;

import org.springframework.security.oauth2.jwt.Jwt;

public interface UtilisateurService extends AbstractService<UtilisateurDto>{

    public Jwt getAuthenticatedUser();
}
