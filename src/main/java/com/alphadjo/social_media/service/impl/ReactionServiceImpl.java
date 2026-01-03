package com.alphadjo.social_media.service.impl;

import com.alphadjo.social_media.dto.reaction.ReactionDto;
import com.alphadjo.social_media.entity.Publication;
import com.alphadjo.social_media.entity.Reaction;
import com.alphadjo.social_media.entity.Utilisateur;
import com.alphadjo.social_media.repository.contract.PublicationRepository;
import com.alphadjo.social_media.repository.contract.ReactionRepository;
import com.alphadjo.social_media.repository.contract.UtilisateurRepository;
import com.alphadjo.social_media.service.contract.ReactionService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class ReactionServiceImpl implements ReactionService {

    private final ReactionRepository reactionRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final PublicationRepository publicationRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public List<ReactionDto> findAll() {
        return List.of();
    }

    @Override
    public ReactionDto findById(Long id) {
        return null;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @Override
    public ReactionDto save(ReactionDto reactionDto) {

        Utilisateur utilisateur = utilisateurRepository.findById(reactionDto.getUtilisateurId()).orElseThrow(
                () -> new RuntimeException("User not found with in the system"));

        Publication publication = publicationRepository.findById(reactionDto.getPublicationId()).orElseThrow(
                () -> new RuntimeException("Publication not found with in the system"));

        if(reactionRepository.findByPublicationAndUtilisateur(publication.getId(), utilisateur.getId()).isPresent()){
            throw new RuntimeException("You have already reacted to this publication");
        }

        Reaction reaction = ReactionDto.toEntity(reactionDto);
        reaction.setUtilisateur(utilisateur);
        reaction.setPublication(publication);
        Reaction savedReaction = reactionRepository.save(reaction);
        ReactionDto dto = ReactionDto.fromEntity(savedReaction);
        simpMessagingTemplate.convertAndSend("/queue/like/"+publication.getId(), dto);
        return ReactionDto.fromEntity(savedReaction);
    }

    @Override
    public ReactionDto update(ReactionDto reactionDto, Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
