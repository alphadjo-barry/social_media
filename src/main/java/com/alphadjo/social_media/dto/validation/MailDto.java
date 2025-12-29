package com.alphadjo.social_media.dto.validation;

public record MailDto(
        String code,
        String email,
        String firstName,
        String lastName
) { }
