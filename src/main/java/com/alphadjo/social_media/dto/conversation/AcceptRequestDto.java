package com.alphadjo.social_media.dto.conversation;

public record AcceptRequestDto(
        Long envoyeurId,
        Long recepteurId
) { }
