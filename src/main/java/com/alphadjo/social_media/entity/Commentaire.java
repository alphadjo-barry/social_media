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
    @Table(name = "commentaires")
    public class Commentaire extends AbstractEntity{

        private String contenu;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "publication_id", nullable = false)
        private Publication publication;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "utilisateur_id", nullable = false)
        private Utilisateur auteur;
    }
