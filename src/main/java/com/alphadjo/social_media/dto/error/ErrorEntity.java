package com.alphadjo.social_media.dto.error;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorEntity(
        int status,
        String error,
        String message,
        String path,
        LocalDateTime timestamp
) { }
