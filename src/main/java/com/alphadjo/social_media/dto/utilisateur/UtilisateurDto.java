package com.alphadjo.social_media.dto.utilisateur;

import com.alphadjo.social_media.dto.role.RoleDto;

import com.alphadjo.social_media.entity.Utilisateur;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;

import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Data
public class UtilisateurDto {

    private Long id;
    @NotBlank(message = "firstName is required")

    private String firstName;
    @NotBlank(message = "lastName is required")

    private String lastName;
    @NotBlank(message = "email is required")

    @Email(message = "email is not valid")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 8, message = "password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial."
    )
    private String password;

    @NotBlank(message = "phone is required")
    @Pattern(regexp = "^\\+33[1-9][0-9]{8}$", message = "Phone number is not valid")
    private String phone;

    private Set<RoleDto> roles;

    public static Utilisateur toEntity(UtilisateurDto dto){

        return Utilisateur.builder()
                .firstName(dto.firstName)
                .lastName(dto.lastName.toLowerCase())
                .email(dto.email)
                .password(dto.password)
                .phone(dto.phone)
                .build();
    }

    public static UtilisateurDto fromEntity(Utilisateur utilisateur){

        return UtilisateurDto.builder()
                .id(utilisateur.getId())
                .firstName(utilisateur.getFirstName())
                .lastName(utilisateur.getLastName().toUpperCase())
                .email(utilisateur.getEmail())
                .phone(utilisateur.getPhone())
                .roles(
                        utilisateur.getRoles() != null ? utilisateur.getRoles().stream()
                                .map(RoleDto::fromEntity)
                                .collect(Collectors.toSet()) : new HashSet<>()
                )
                .build();
    }
}
