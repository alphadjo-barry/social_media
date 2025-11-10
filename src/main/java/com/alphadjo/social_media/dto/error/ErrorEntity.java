package com.alphadjo.social_media.dto.error;

import lombok.Builder;

@Builder
public record ErrorEntity(
        String code,
        String message
) { }
