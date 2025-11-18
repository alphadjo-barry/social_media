package com.alphadjo.social_media.service.contract;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface MinioService {

    void uploadProfilePicture(String filename, InputStream inputStream, String contentType, String bucketName);
    String uploadPublicationPicture(String filename, InputStream inputStream, String contentType, String bucketName);
    String pictureName(MultipartFile file);
    String getAuthUserPictureProfile();
    String getPictureProfileUrlByFileName(String fileName);
}
