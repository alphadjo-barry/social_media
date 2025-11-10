package com.alphadjo.social_media.service.impl;

import com.alphadjo.social_media.entity.Utilisateur;
import com.alphadjo.social_media.repository.contract.UtilisationRepository;
import com.alphadjo.social_media.service.contract.MinioService;
import com.alphadjo.social_media.service.contract.UtilisateurService;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;

import io.minio.http.Method;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;
    private final UtilisateurService utilisateurService;
    private final UtilisationRepository utilisationRepository;

    @Override
    public void uploadFile(String filename, InputStream inputStream, String contentType, String bucketName) {

        try{

            Jwt jwt = utilisateurService.getAuthenticatedUser();

            Utilisateur utilisateur = utilisationRepository.findByEmail(jwt.getSubject()).orElseThrow(
                    () -> new EntityNotFoundException("User not found with username: " + jwt.getSubject() + " in the system"));

            utilisateur.setPhotoOriginalName(filename);
            utilisationRepository.save(utilisateur);

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filename)
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType(contentType)
                            .build()
            );
        }
        catch (Exception e){
            throw new RuntimeException("Error while uploading file to Minio, from service");
        }
    }

    public String pictureName(MultipartFile file){

        String originalFilename = file.getOriginalFilename();
        String extension = "";

        if(originalFilename != null && originalFilename.contains(".")){
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            if(!List.of(".jpg", ".jpeg", ".png").contains(extension)){
                throw new IllegalArgumentException("File extension is not supported, only .jpg, .jpeg and .png are supported");
            }
        }

        return UUID.randomUUID() + extension;
    }


    @Override
    public String getProfilePicture() {
        Jwt jwt = utilisateurService.getAuthenticatedUser();

        Utilisateur utilisateur = utilisationRepository.findByEmail(jwt.getSubject()).orElseThrow(
                () -> new RuntimeException("User not found with username: " + jwt.getSubject() + " in the system"));

        String filename = utilisateur.getPhotoOriginalName();
        if(filename == null || filename.isEmpty()){
            throw new RuntimeException("User has no profile picture");
        }

        return this.getPresignedObjectUrl(filename) ;
    }

    private String getPresignedObjectUrl(String filename) {

        try{
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket("profiles")
                            .object(filename)
                            .expiry(60 * 60 * 24)
                            .build()
            );
        }
        catch (Exception e){
            throw new RuntimeException("Error where generating file URL.");
        }

    }
}
