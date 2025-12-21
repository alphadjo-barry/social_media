package com.alphadjo.social_media.service.contract;

import com.alphadjo.social_media.dto.ConversationDto;

import java.util.Set;

public interface ConversationService extends AbstractService<ConversationDto>{
    Set<ConversationDto> findByEnvoyeurId();
    Set<ConversationDto> findByRecepteurId();
}
