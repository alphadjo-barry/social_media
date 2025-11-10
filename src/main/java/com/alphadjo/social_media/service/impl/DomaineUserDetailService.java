package com.alphadjo.social_media.service.impl;

import com.alphadjo.social_media.entity.Utilisateur;
import com.alphadjo.social_media.repository.contract.UtilisationRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class DomaineUserDetailService implements UserDetailsService {

    private final UtilisationRepository utilisationRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Utilisateur utilisateur = utilisationRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        Set<SimpleGrantedAuthority> authorities = getAuthorities(utilisateur);

        return new User(
                utilisateur.getEmail(),
                utilisateur.getPassword(),
                utilisateur.isEnabled(),
                utilisateur.isEnabled(),
                utilisateur.isEnabled(),
                utilisateur.isEnabled(),
                authorities);
    }

    public Set<SimpleGrantedAuthority> getAuthorities(Utilisateur utilisateur) {
        return utilisateur.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toSet());
    }
}
