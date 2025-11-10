package com.alphadjo.social_media.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "validations")
public class Validation{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private Instant createdAt;

    @Column(nullable = true)
    private Instant expiredAt;

    @Column(nullable = true)
    private Instant validatedAt;

    private boolean isValidated = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;
}
