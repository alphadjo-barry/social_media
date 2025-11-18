package com.alphadjo.social_media.service.contract;

import org.springframework.security.oauth2.jwt.Jwt;

public interface AuthenticationUserService {
    Jwt getAuthenticatedUser();
}
