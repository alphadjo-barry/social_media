package com.alphadjo.social_media.service.impl;

import com.alphadjo.social_media.entity.Utilisateur;
import com.alphadjo.social_media.repository.contract.UtilisateurRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final AuthenticationManager authenticationManager;
    private final UtilisateurRepository utilisateurRepository;

    @Value("${app.base-url}")
    private String baseUrl;

    @Value("${app.default-image}")
    private String defaultAvatar;

    public JwtService(JwtEncoder jwtEncoder, AuthenticationManager authenticationManager, UtilisateurRepository utilisateurRepository) {
        this.jwtEncoder = jwtEncoder;
        this.authenticationManager = authenticationManager;
        this.utilisateurRepository = utilisateurRepository;
    }

    public String generateToken(String email, String password){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticateUser(authenticationToken);

        return createToken(authentication);
    }

    private Authentication authenticateUser(UsernamePasswordAuthenticationToken authenticationToken) {
        try {

            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication;

        } catch (DisabledException e) {
            throw new DisabledException("Compte utilisateur non activé pour l'instant !");
        } catch (Exception e){
                throw new RuntimeException("Username or password is incorrect");
        }
    }

    private String createToken(Authentication authentication) {
        Set<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        JwtClaimsSet claims = getClaimsFromToken(authentication, authorities);
        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    private JwtClaimsSet getClaimsFromToken(Authentication authentication, Set<String> authorities) {

        Utilisateur utilisateur = utilisateurRepository
                .findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        String picture =
                utilisateur.getPicturePath() != null && !utilisateur.getPicturePath().isBlank()
                        ? utilisateur.getPicturePath()
                        : baseUrl + defaultAvatar;

        return JwtClaimsSet.builder()
                .subject(authentication.getName())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(1, ChronoUnit.DAYS))
                .claim("roles", authorities)
                .claim("fullName", utilisateur.getFirstName() + " " + utilisateur.getLastName())
                .claim("email", utilisateur.getEmail())
                .claim("userId", utilisateur.getId())
                .claim("picturePath", picture)
                .build();
    }
}