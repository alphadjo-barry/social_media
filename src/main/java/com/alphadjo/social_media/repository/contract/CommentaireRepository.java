package com.alphadjo.social_media.repository.contract;

import com.alphadjo.social_media.entity.Commentaire;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {

    @Query("SELECT c FROM Commentaire c JOIN FETCH c.auteur JOIN FETCH c.publication WHERE c.publication.id = :publicationId")
    List<Commentaire> findByPublicationId(@Param("publicationId") Long publicationId);
}
