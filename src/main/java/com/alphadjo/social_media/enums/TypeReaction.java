package com.alphadjo.social_media.enums;

import lombok.Getter;

@Getter
public enum TypeReaction {

    ADORABLE("Adorable"),
    LIKE("Cool"),
    BRAVO("Bravo"),
    RIRE("Rire");

    private final String label;

    TypeReaction(String label) {
        this.label = label;
    }

}
