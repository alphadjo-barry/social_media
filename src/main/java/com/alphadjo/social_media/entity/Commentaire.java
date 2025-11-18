package com.alphadjo.social_media.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "commentaires",
        uniqueConstraints = @UniqueConstraint(columnNames = {"publication_id", "utilisateur_id"}))
public class Commentaire extends AbstractEntity{

    @Lob
    private String contenu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publication_id")
    private Publication publication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur auteur;
}
