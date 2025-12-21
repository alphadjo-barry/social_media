package com.alphadjo.social_media.service.impl;

import com.alphadjo.social_media.dto.ConversationDto;
import com.alphadjo.social_media.entity.Conversation;
import com.alphadjo.social_media.entity.Utilisateur;
import com.alphadjo.social_media.enums.ConversationStatus;
import com.alphadjo.social_media.repository.contract.ConversationRepository;
import com.alphadjo.social_media.repository.contract.UtilisateurRepository;
import com.alphadjo.social_media.service.contract.AuthenticationUserService;
import com.alphadjo.social_media.service.contract.ConversationService;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final AuthenticationUserService authenticationUserService;

    @Override
    public List<ConversationDto> findAll() {
        return List.of();
    }

    @Override
    public ConversationDto findById(Long id) {
        return null;
    }

    @Override
    public ConversationDto save(ConversationDto conversationDto) {
        Utilisateur envoyeur = utilisateurRepository.findById(conversationDto.getEnvoyeurId()).orElseThrow(
                () -> new RuntimeException("Send is not found in the system"));

        Utilisateur recepteur = utilisateurRepository.findById(conversationDto.getRecepteurId()).orElseThrow(
                () -> new RuntimeException("Receive is not found in the system"));

        Conversation conversation = ConversationDto.toEntity(conversationDto);
        conversation.setStatus(ConversationStatus.PENDING);
        conversation.setEnvoyeur(envoyeur);
        conversation.setRecepteur(recepteur);

        return ConversationDto.fromEntity(conversationRepository.save(conversation));
    }

    @Override
    public ConversationDto update(ConversationDto conversationDto, Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Set<ConversationDto> findByEnvoyeurId() {

        Jwt jwt = authenticationUserService.getAuthenticatedUser();
        Utilisateur utilisateur = utilisateurRepository.findByEmail(jwt.getSubject()).orElseThrow(
                () -> new RuntimeException("User not found with username: " + jwt.getSubject() + " in the system"));

        return this.conversationRepository.findByEnvoyeurId(utilisateur.getId()).stream()
                .map(ConversationDto::fromEntity)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<ConversationDto> findByRecepteurId() {

        Jwt jwt = authenticationUserService.getAuthenticatedUser();
        Utilisateur utilisateur = utilisateurRepository.findByEmail(jwt.getSubject()).orElseThrow(
                () -> new RuntimeException("User not found with username: " + jwt.getSubject() + " in the system"));
      return this.conversationRepository.findByRecepteurId(utilisateur.getId()).stream()
              .map(ConversationDto::fromEntity)
              .collect(Collectors.toSet());
    }
}
