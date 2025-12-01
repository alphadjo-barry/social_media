package com.alphadjo.social_media.dto.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ResendCodeDto(
        @NotBlank(message = "mail address is required.")
        @Pattern(regexp = "^[a-zA-Z0-9._]+@[a-z]+\\.[a-z]{2,}$", message = "mail address must be valid.")
        String email
) { }
