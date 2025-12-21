package com.alphadjo.social_media.service.impl;

import com.alphadjo.social_media.entity.Utilisateur;
import com.alphadjo.social_media.exceptions.FileStorageException;
import com.alphadjo.social_media.repository.contract.UtilisateurRepository;
import com.alphadjo.social_media.service.contract.AuthenticationUserService;
import com.alphadjo.social_media.service.contract.MinioService;
import io.minio.*;

import io.minio.http.Method;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;
    private final UtilisateurRepository utilisateurRepository;
    private final AuthenticationUserService authenticationUserService;

    @Override
    public void uploadProfilePicture(String filename, InputStream inputStream, String contentType, String bucketName) {

        try{
            Jwt jwt =authenticationUserService.getAuthenticatedUser();

            Utilisateur utilisateur = utilisateurRepository.findByEmail(jwt.getSubject()).orElseThrow(
                    () -> new EntityNotFoundException("User not found with username: " + jwt.getSubject() + " in the system"));

            utilisateur.setPhotoOriginalName(filename);
            utilisateurRepository.save(utilisateur);

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filename)
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType(contentType)
                            .build());

            String pictureUrl = this.getUrlPictureByFilenameAndByBucket(filename, "profiles");
            utilisateur.setPicturePath(pictureUrl);
            utilisateurRepository.save(utilisateur);
        }
        catch (Exception e){
            throw new RuntimeException("Error while uploading profile file to Minio, from service");
        }
    }

    @Override
    public String uploadPublicationPicture(String filename, InputStream inputStream, String contentType, String bucketName) {
        try{

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filename)
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType(contentType)
                            .build());

            return this.getUrlPictureByFilenameAndByBucket(filename, bucketName);
        }
        catch (Exception e){
            log.info(e.getMessage());
            throw new RuntimeException("Error while uploading publication file to Minio, from service");
        }
    }

    public String pictureName(MultipartFile file) throws FileStorageException, IllegalArgumentException{

        if (file == null || file.isEmpty()) {
            throw new FileStorageException("File is empty", new Throwable("Missing of empty file"));
        }

        long maxSize = 6 * 1024 * 1024;

        if(file.getSize() > maxSize){
            throw new IllegalArgumentException("File size is too large, max size is 5 Mo");
        }

        String originalFilename = file.getOriginalFilename();
        String extension = "";

            if(originalFilename != null && originalFilename.contains(".")){
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            if(!List.of(".jpg", ".jpeg", ".png").contains(extension)){
                throw new IllegalArgumentException("Only the jpg, jpeg and png are supported");
            }
        }

        return UUID.randomUUID() + extension;
    }

    @Override
    public String getAuthUserPictureProfile() {
        Jwt jwt = authenticationUserService.getAuthenticatedUser();

        Utilisateur utilisateur = utilisateurRepository.findByEmail(jwt.getSubject()).orElseThrow(
                () -> new RuntimeException("User not found with username: " + jwt.getSubject() + " in the system"));

        String filename = utilisateur.getPhotoOriginalName();

        if(filename == null || filename.isEmpty()){
            throw new RuntimeException("User has no profile picture");
        }

        return this.getFileFromBucket(filename, "profiles") ;
    }

    @Override
    public String getUrlPictureByFilenameAndByBucket(String fileName, String bucketName) {

        Jwt jwt = authenticationUserService. getAuthenticatedUser();

        Utilisateur utilisateur = utilisateurRepository.findByEmail(jwt.getSubject()).orElseThrow(
                () -> new RuntimeException("User not found with in the system"));

        if(fileName == null || fileName.isEmpty()){
            throw new RuntimeException("The picture is empty");
        }

        return this.getFileFromBucket(fileName, bucketName);
    }

    private String getFileFromBucket(String filename, String bucketName) {

        try{
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(filename)
                            .build()
            );
        }
        catch (Exception e){
            throw new RuntimeException("Error where generating file URL.");
        }
    }

}
