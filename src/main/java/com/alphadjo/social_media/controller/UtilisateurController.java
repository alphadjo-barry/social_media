package com.alphadjo.social_media.controller;

import com.alphadjo.social_media.dto.utilisateur.UtilisateurDto;
import com.alphadjo.social_media.service.contract.MinioService;
import com.alphadjo.social_media.service.contract.UtilisateurService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;
    private final MinioService minioService;

    @PostMapping
    public ResponseEntity<?> createUtilisateur(@Valid @RequestBody UtilisateurDto utilisateurDto){
        return ResponseEntity.ok(utilisateurService.save(utilisateurDto));
    }

    @PostMapping("/profile-picture")
    public ResponseEntity<String> uploadProfilePicture(@RequestParam("file") MultipartFile file) throws IOException {

            String fileName = minioService.pictureName(file);

            minioService.uploadFile(
                    fileName,
                    file.getInputStream(),
                    file.getContentType(),
                    "profiles"
            );

            return ResponseEntity.ok(fileName);

    }

    @GetMapping("/profile-picture")
    public ResponseEntity<String> getProfilePicture() throws IOException {
       return ResponseEntity.ok(minioService.getProfilePicture());
    }
}
