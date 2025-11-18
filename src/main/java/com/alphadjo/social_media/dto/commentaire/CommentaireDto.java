package com.alphadjo.social_media.dto.commentaire;

import com.alphadjo.social_media.dto.utilisateur.UtilisateurDto;

import com.alphadjo.social_media.entity.Commentaire;
import jakarta.persistence.Lob;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentaireDto {

    private Long id;
    @Lob
    private String contenu;

    private Long  publicationId;

    private UtilisateurDto auteur;

    public static Commentaire toEntity(CommentaireDto commentaireDto) {

        if(commentaireDto == null) return null;

        return Commentaire.builder()
                .contenu(commentaireDto.contenu)
                .auteur(UtilisateurDto.toEntity(commentaireDto.auteur))
                .build();
    }

    public static CommentaireDto fromEntity(Commentaire commentaire) {

        if(commentaire == null) return null;

        return CommentaireDto.builder()
                .id(commentaire.getId())
                .contenu(commentaire.getContenu())
                .publicationId(commentaire.getPublication().getId())
                .auteur(UtilisateurDto.fromEntity(commentaire.getAuteur()))
                .build();
    }

}
