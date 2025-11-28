package com.alphadjo.social_media.dto.validation;

public record MessageRabbitDto(
        String code,
        String email,
        String firstName,
        String lastName
) { }
