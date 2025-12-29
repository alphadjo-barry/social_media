package com.alphadjo.social_media.service.impl;

import com.alphadjo.social_media.dto.conversation.AcceptRequestDto;
import com.alphadjo.social_media.dto.conversation.ConversationDto;
import com.alphadjo.social_media.entity.Conversation;
import com.alphadjo.social_media.entity.Utilisateur;
import com.alphadjo.social_media.enums.ConversationStatus;
import com.alphadjo.social_media.repository.contract.ConversationRepository;
import com.alphadjo.social_media.repository.contract.UtilisateurRepository;
import com.alphadjo.social_media.service.contract.AuthenticationUserService;
import com.alphadjo.social_media.service.contract.ConversationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
@AllArgsConstructor
@Service
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final AuthenticationUserService authenticationUserService;
    private final SimpMessagingTemplate simpMessagingTemplate;

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

        Optional<Conversation> existingConversation = this.conversationRepository.findByEnvoyeurIdAndRecepteurId(envoyeur.getId(), recepteur.getId());

        if(existingConversation.isPresent()){
            throw new RuntimeException("Vous avez une demande en attente avec ce destinataire");
        }

        Conversation conversation = ConversationDto.toEntity(conversationDto);
        conversation.setStatus(ConversationStatus.PENDING);
        conversation.setEnvoyeur(envoyeur);
        conversation.setRecepteur(recepteur);
        ConversationDto dto =  ConversationDto.fromEntity(conversationRepository.save(conversation));
        simpMessagingTemplate.convertAndSend("/queue/receive/"+recepteur.getId(), dto);
        simpMessagingTemplate.convertAndSend("/queue/send/"+envoyeur.getId(), dto);

        return dto;
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

    @Override
    public ConversationDto acceptedConversation(AcceptRequestDto acceptRequestDto, Long id) {
       Conversation conversation = this.conversationRepository.findById(id).orElseThrow(
               () -> new RuntimeException("Conversation not found with id: " + id + " in the system"));
       conversation.setStatus(ConversationStatus.ACCEPTED);
       Conversation conversationUpdated = conversationRepository.save(conversation);
       ConversationDto dto = ConversationDto.fromEntity(conversationUpdated);

       simpMessagingTemplate.convertAndSend("/queue/receive/"+acceptRequestDto.recepteurId(), dto);
       simpMessagingTemplate.convertAndSend("/queue/send/"+acceptRequestDto.envoyeurId(), dto);

        return dto;
    }

}
