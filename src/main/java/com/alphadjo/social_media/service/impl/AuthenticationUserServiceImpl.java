package com.alphadjo.social_media.service.impl;

import com.alphadjo.social_media.service.contract.AuthenticationUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationUserServiceImpl implements AuthenticationUserService {

    @Override
    public Jwt getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof Jwt){
            return (Jwt) authentication.getPrincipal() ;
        }
        return null;
    }
}
