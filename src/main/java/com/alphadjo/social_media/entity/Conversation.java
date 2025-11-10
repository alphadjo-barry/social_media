package com.alphadjo.social_media.entity;

import com.alphadjo.social_media.enums.ConversationStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.Instant;

@Entity
public class Conversation extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "envoyeur_id")
    private Utilisateur envoyeur;

    @ManyToOne
    @JoinColumn(name = "recepteur_id")
    private Utilisateur recepteur;

    private ConversationStatus status;

    private Instant acceptedOrRejectedAt;
}
