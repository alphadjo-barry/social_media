package com.alphadjo.social_media.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "likes", uniqueConstraints =
        @UniqueConstraint(columnNames = {"utilisateur_id", "publication_id"})
)
public class Like extends AbstractEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publication_id")
    private Publication publication;
}
