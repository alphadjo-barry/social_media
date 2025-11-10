package com.alphadjo.social_media.service.contract;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface MinioService {

    public void uploadFile(String filename, InputStream inputStream, String contentType, String bucketName);
    public String pictureName(MultipartFile file);

    public String getProfilePicture();
}
