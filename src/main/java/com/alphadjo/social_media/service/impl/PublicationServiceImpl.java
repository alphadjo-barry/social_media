package com.alphadjo.social_media.service.impl;

import com.alphadjo.social_media.dto.publication.PublicationDto;
import com.alphadjo.social_media.entity.Publication;
import com.alphadjo.social_media.entity.PublicationPicture;
import com.alphadjo.social_media.entity.Utilisateur;
import com.alphadjo.social_media.repository.contract.PublicationPictureRepository;
import com.alphadjo.social_media.repository.contract.PublicationRepository;
import com.alphadjo.social_media.repository.contract.UtilisateurRepository;
import com.alphadjo.social_media.service.contract.AuthenticationUserService;
import com.alphadjo.social_media.service.contract.MinioService;
import com.alphadjo.social_media.service.contract.PublicationService;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PublicationServiceImpl implements PublicationService {

    private final AuthenticationUserService authenticationUserService;
    private final UtilisateurRepository utilisateurRepository;
    private final PublicationRepository publicationRepository;
    private final MinioService minioService;
    private final PublicationPictureRepository publicationPictureRepository;

    @Override
    public PublicationDto savePublication(PublicationDto publicationDto) throws Exception {

        Jwt jwt = authenticationUserService.getAuthenticatedUser();

        Utilisateur utilisateur = utilisateurRepository.findByEmail(jwt.getSubject()).orElseThrow(
                ()-> new EntityNotFoundException("The user the want to publish is not found !"));

        Publication publication = new Publication();
        publication.setLegende(publicationDto.getLegende());
        publication.setAuteur(utilisateur);

        Publication savedPublication = publicationRepository.save(publication);

        if (publicationDto.getFiles() != null) {
            for (MultipartFile file : publicationDto.getFiles()) {

                String filename = minioService.pictureName(file);

                String picturePath = minioService.uploadPublicationPicture(
                        filename,
                        file.getInputStream(),
                        file.getContentType(),
                        "publications");

                PublicationPicture pic = PublicationPicture.builder()
                        .publication(publication)
                        .picturePath(picturePath)
                        .photoOriginalName(filename)
                        .build();

                publicationPictureRepository.save(pic);
            }
        }

        return PublicationDto.fromEntity(publication);
    }

    @Override
    public List<PublicationDto> findAll() {
        return this.publicationRepository.findAll().stream()
                        .map(PublicationDto::fromEntity)
                        .toList();
    }

    @Override
    public PublicationDto findById(Long id) {
        return null;
    }

    @Override
    public PublicationDto save(PublicationDto publicationDto) {
        return null;
    }

    @Override
    public PublicationDto update(PublicationDto publicationDto, Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
