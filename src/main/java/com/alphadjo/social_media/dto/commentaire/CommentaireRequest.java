package com.alphadjo.social_media.dto.commentaire;

import jakarta.validation.constraints.NotBlank;

public record CommentaireRequest(
        @NotBlank(message = "Contenu is required")
        String contenu,
        @NotBlank(message = "Publication is required")
        int publicationId
) { }
