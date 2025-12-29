package com.alphadjo.social_media.service.contract;

import com.alphadjo.social_media.dto.conversation.AcceptRequestDto;
import com.alphadjo.social_media.dto.conversation.ConversationDto;

import java.util.Set;

public interface ConversationService extends AbstractService<ConversationDto>{
    Set<ConversationDto> findByEnvoyeurId();
    Set<ConversationDto> findByRecepteurId();
    ConversationDto acceptedConversation(AcceptRequestDto dt, Long id);
}
