package com.alphadjo.social_media.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
@Table(name = "roles")
public class Role extends AbstractEntity{

    private String name;

    private boolean isActive = false;
}
