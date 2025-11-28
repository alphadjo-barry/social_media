package com.alphadjo.social_media.dto.publication;

import com.alphadjo.social_media.dto.commentaire.CommentaireDto;
import com.alphadjo.social_media.dto.picture.PublicationPictureDto;
import com.alphadjo.social_media.dto.reaction.ReactionDto;
import com.alphadjo.social_media.dto.utilisateur.UtilisateurDto;

import com.alphadjo.social_media.entity.Publication;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicationDto {

    private Long id;

    @NotBlank(message = "The legend is required")
    private String legende;

    private UtilisateurDto auteur;

    private MultipartFile[] files;

    private long reactions;
    private long commentaires;
    private Set<PublicationPictureDto> pictures;

    public static Publication toEntity(PublicationDto publicationDto){

        if(publicationDto == null) return null;

        return Publication.builder()
                .legende(publicationDto.getLegende())
                .build();
    }

    public static PublicationDto fromEntity(Publication publication){

        if(publication == null) return null;

        return PublicationDto.builder()
                .id(publication.getId())
                .legende(publication.getLegende())
                .auteur(UtilisateurDto.fromEntity(publication.getAuteur()))
                .reactions(
                        publication.getReactions() != null ? publication.getReactions().stream()
                                .distinct().count() : 0)
                .commentaires(
                        publication.getCommentaires() != null ? publication.getCommentaires().stream()
                                .distinct().count() : 0)
                .pictures(
                        publication.getPictures() != null ? publication.getPictures().stream()
                                .map(PublicationPictureDto::fromEntity)
                                .collect(Collectors.toSet()) : new HashSet<>())
                .build();
    }

}
