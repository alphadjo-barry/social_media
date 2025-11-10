package com.alphadjo.social_media.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;

import java.time.Instant;

@Entity
public class Message extends AbstractEntity{

    private String contenu;

    @ManyToOne
    @JoinColumn(name = "envoyeur_id")
    private Utilisateur envoyeur;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    private Instant readAt;
    private boolean isRead = false;
}
