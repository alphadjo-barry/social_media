package com.alphadjo.social_media.dto.commentaire;

import com.alphadjo.social_media.dto.utilisateur.UtilisateurDto;

import com.alphadjo.social_media.entity.Commentaire;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentaireDto {

    private Long id;

    @NotBlank(message = "The content is required")
    private String contenu;

    @NotNull(message = "Publication is required")
    private Long  publicationId;

    private UtilisateurDto auteur;

    private LocalDateTime createdAt;

    public static Commentaire toEntity(CommentaireDto commentaireDto) {

        if(commentaireDto == null) return null;

        return Commentaire.builder()
                .contenu(commentaireDto.contenu)
                .build();
    }

    public static CommentaireDto fromEntity(Commentaire commentaire) {

        if(commentaire == null) return null;

        return CommentaireDto.builder()
                .id(commentaire.getId())
                .contenu(commentaire.getContenu())
                .publicationId(commentaire.getPublication().getId())
                .auteur(UtilisateurDto.fromEntity(commentaire.getAuteur()))
                .createdAt(commentaire.getCreatedAt())
                .build();
    }

}
