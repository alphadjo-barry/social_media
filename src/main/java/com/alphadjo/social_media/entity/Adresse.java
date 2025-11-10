package com.alphadjo.social_media.entity;

import jakarta.persistence.Entity;

@Entity
public class Adresse extends AbstractEntity{

    private String numero;
    private String rue;
    private String codePostal;
    private String ville;

    private boolean isMain = false;
}
