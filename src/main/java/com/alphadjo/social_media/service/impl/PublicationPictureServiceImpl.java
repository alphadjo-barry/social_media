package com.alphadjo.social_media.service.impl;

import com.alphadjo.social_media.entity.PublicationPicture;
import com.alphadjo.social_media.service.contract.PublicationPictureService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicationPictureServiceImpl implements PublicationPictureService {

    @Override
    public List<PublicationPicture> findAll() {
        return List.of();
    }

    @Override
    public PublicationPicture findById(Long id) {
        return null;
    }

    @Override
    public PublicationPicture save(PublicationPicture publicationPicture) {
        return null;
    }

    @Override
    public PublicationPicture update(PublicationPicture publicationPicture, Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
