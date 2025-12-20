package com.alphadjo.social_media.service.impl;

import com.alphadjo.social_media.dto.commentaire.CommentaireDto;
import com.alphadjo.social_media.entity.Commentaire;
import com.alphadjo.social_media.entity.Publication;
import com.alphadjo.social_media.entity.Utilisateur;
import com.alphadjo.social_media.repository.contract.CommentaireRepository;
import com.alphadjo.social_media.repository.contract.PublicationRepository;
import com.alphadjo.social_media.repository.contract.UtilisateurRepository;
import com.alphadjo.social_media.service.contract.AuthenticationUserService;
import com.alphadjo.social_media.service.contract.CommentaireService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class CommentaireServiceImpl implements CommentaireService {

    private final CommentaireRepository commentaireRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final PublicationRepository publicationRepository;
    private final AuthenticationUserService authenticationUserService;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public List<CommentaireDto> findAll() {
        return List.of();
    }

    @Override
    public CommentaireDto findById(Long id) {
        return null;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @Override
    public CommentaireDto save(CommentaireDto commentaireDto) {

        Jwt jwt = authenticationUserService.getAuthenticatedUser();
        Utilisateur utilisateur = utilisateurRepository.findByEmail(jwt.getSubject()).orElseThrow(
                () -> new RuntimeException("User not found with in the system"));

        Publication publication = publicationRepository.findById(commentaireDto.getPublicationId()).orElseThrow(
                () -> new RuntimeException("Publication not found with in the system"));

        Commentaire commentaire = CommentaireDto.toEntity(commentaireDto);
        commentaire.setAuteur(utilisateur);
        commentaire.setPublication(publication);
        log.info("Commentaire saved : {}", commentaire);
        Commentaire savedCommentaire = commentaireRepository.save(commentaire);
        messagingTemplate.convertAndSend("/topic/publications/"+publication.getId()+"/commentaires", CommentaireDto.fromEntity(savedCommentaire));
        return CommentaireDto.fromEntity(savedCommentaire);
    }

    @Override
    public CommentaireDto update(CommentaireDto commentaireDto, Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<CommentaireDto> findByPublicationId(Long publicationId) {
        return commentaireRepository.findByPublicationId(publicationId)
                .stream()
                .map(CommentaireDto::fromEntity)
                .collect(Collectors.toList());
    }

}
