package com.alphadjo.social_media.controller;

import com.alphadjo.social_media.dto.password.PasswordRequest;
import com.alphadjo.social_media.dto.utilisateur.UtilisateurDto;
import com.alphadjo.social_media.dto.validation.ResendCodeDto;
import com.alphadjo.social_media.dto.validation.ValidationRequest;
import com.alphadjo.social_media.dto.validation.ValidationResponse;
import com.alphadjo.social_media.service.contract.MinioService;
import com.alphadjo.social_media.service.contract.UtilisateurService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;
    private final MinioService minioService;

    @GetMapping
    public ResponseEntity<List<UtilisateurDto>> findAll(){
        return ResponseEntity.ok(utilisateurService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurDto> findById(@PathVariable Long id){
        return ResponseEntity.ok(utilisateurService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurDto> update(@Valid @RequestBody UtilisateurDto utilisateurDto, @PathVariable Long id){
        return ResponseEntity.ok(utilisateurService.update(utilisateurDto, id));
    }

    @PostMapping("/user")
    public ResponseEntity<UtilisateurDto> createUser(@Valid @RequestBody UtilisateurDto utilisateurDto){
        return ResponseEntity.ok(utilisateurService.save(utilisateurDto));
    }

    @PostMapping("/admin")
    public ResponseEntity<UtilisateurDto> createAdmin(@Valid @RequestBody UtilisateurDto utilisateurDto){
        return ResponseEntity.ok(utilisateurService.saveAdmin(utilisateurDto));
    }

    @PostMapping("/profile-picture")
    public ResponseEntity<String> uploadProfilePicture(@RequestParam("file") MultipartFile file) throws IOException {

            String fileName = minioService.pictureName(file);

            minioService.uploadProfilePicture(
                    fileName,
                    file.getInputStream(),
                    file.getContentType(),
                    "profiles"
            );

            return ResponseEntity.ok(fileName);
    }

    @GetMapping("/profile-picture")
    public ResponseEntity<String> getProfilePicture() throws IOException {
       return ResponseEntity.ok(minioService.getAuthUserPictureProfile());
    }

    @PutMapping("/enableAccount")
    public ResponseEntity<ValidationResponse> enableAccount(@Valid @RequestBody ValidationRequest request){
        return ResponseEntity.ok(new ValidationResponse(utilisateurService.enableAccount(request)));
    }

    @PutMapping("/enabled/{id}")
    public ResponseEntity<Boolean> enabledById(@PathVariable Long id){
        return ResponseEntity.ok(utilisateurService.enableById(id));
    }

    @PutMapping("/disabled/{id}")
    public ResponseEntity<Boolean> disabledById(@PathVariable Long id){
        return ResponseEntity.ok(utilisateurService.disableById(id));
    }

    @PutMapping("/changePassword/{id}")
    public ResponseEntity<String> changePassword(@Valid @RequestBody PasswordRequest passwordRequest,@PathVariable Long id ){
        return ResponseEntity.ok(utilisateurService.passwordChange(id, passwordRequest));
    }

    @PutMapping("/resendCode")
    public ResponseEntity<Void> resendCode(@Valid @RequestBody ResendCodeDto dto){
         utilisateurService.resendCode(dto);
         return null;
    }
}
