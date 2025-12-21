package com.alphadjo.social_media.entity;

import com.alphadjo.social_media.enums.ConversationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "conversations")
public class Conversation extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "envoyeur_id")
    private Utilisateur envoyeur;

    @ManyToOne
    @JoinColumn(name = "recepteur_id")
    private Utilisateur recepteur;

    private ConversationStatus status;

    @Column(nullable = true)
    private Instant acceptedOrRejectedAt;
}
