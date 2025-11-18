package com.alphadjo.social_media.entity;

import com.alphadjo.social_media.enums.TypeReaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Entity
@Table(name = "reactions", uniqueConstraints =
        @UniqueConstraint(columnNames = {"utilisateur_id", "publication_id"})
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reaction extends AbstractEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    private TypeReaction type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publication_id")
    private Publication publication;
}
