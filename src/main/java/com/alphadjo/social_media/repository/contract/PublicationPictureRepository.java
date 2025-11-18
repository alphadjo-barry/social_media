package com.alphadjo.social_media.repository.contract;

import com.alphadjo.social_media.entity.PublicationPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicationPictureRepository extends JpaRepository<PublicationPicture, Long> {
}
