package com.alphadjo.social_media.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "adresses")
public class Adresse extends AbstractEntity{

    private String numero;
    private String rue;
    private String codePostal;
    private String ville;

    private boolean isMain = false;
}
