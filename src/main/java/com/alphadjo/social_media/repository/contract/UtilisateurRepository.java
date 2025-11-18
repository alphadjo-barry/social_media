package com.alphadjo.social_media.repository.contract;

import com.alphadjo.social_media.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByEmail(String email);
    Optional<Utilisateur> findByPhotoOriginalName(String filename);
}
