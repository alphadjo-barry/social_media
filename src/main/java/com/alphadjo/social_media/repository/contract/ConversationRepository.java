package com.alphadjo.social_media.repository.contract;


import com.alphadjo.social_media.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    Set<Conversation> findByEnvoyeurId(Long id);
    Set<Conversation> findByRecepteurId(Long id);
}
