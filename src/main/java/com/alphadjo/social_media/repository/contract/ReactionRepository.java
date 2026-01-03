package com.alphadjo.social_media.repository.contract;

import com.alphadjo.social_media.entity.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    @Query("SELECT r FROM Reaction r WHERE r.publication.id = :publicationId AND r.utilisateur.id = :utilisateurId")
    Optional<Reaction> findByPublicationAndUtilisateur(@Param("publicationId") Long publicationId, @Param("utilisateurId") Long utilisateurId);
}
