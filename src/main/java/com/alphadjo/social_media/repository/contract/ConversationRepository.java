package com.alphadjo.social_media.repository.contract;


import com.alphadjo.social_media.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    Set<Conversation> findByEnvoyeurId(Long id);
    Set<Conversation> findByRecepteurId(Long id);

    @Query(""" 
        SELECT c FROM Conversation c 
        WHERE (c.envoyeur.id = :envoyeurId AND c.recepteur.id = :recepteurId)  
        OR (c.envoyeur.id = :recepteurId AND c.envoyeur.id = :envoyeurId)                               
          """)
    Optional<Conversation> findByEnvoyeurIdAndRecepteurId(@Param("envoyeurId") Long envoyeurId, @Param("recepteurId") Long recepteurId);
}
