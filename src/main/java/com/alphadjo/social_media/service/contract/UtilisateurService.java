package com.alphadjo.social_media.service.contract;

import com.alphadjo.social_media.dto.password.PasswordRequest;
import com.alphadjo.social_media.dto.utilisateur.UtilisateurDto;

import com.alphadjo.social_media.dto.validation.ResendCodeDto;
import com.alphadjo.social_media.dto.validation.ValidationRequest;

import java.util.List;

public interface UtilisateurService extends AbstractService<UtilisateurDto>{

    UtilisateurDto saveAdmin(UtilisateurDto dto);
    String enableAccount(ValidationRequest request);

    boolean enableById(Long id);
    boolean disableById(Long id);
    String passwordChange(Long id, PasswordRequest request);
    void resendCode(ResendCodeDto resendCodeDto);
    List<UtilisateurDto> search(String search);

}
