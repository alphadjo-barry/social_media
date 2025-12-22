package com.alphadjo.social_media.dto;

import com.alphadjo.social_media.dto.utilisateur.UtilisateurDto;
import com.alphadjo.social_media.entity.Conversation;
import com.alphadjo.social_media.enums.ConversationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationDto {

    private Long id;

    @NotNull(message = "L'utilisateur demandeur est obligatoire")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long envoyeurId;

    @NotNull(message = "L'utilisateur recepteur est obligatoire")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long recepteurId;

    private UtilisateurDto envoyeur;
    private UtilisateurDto recepteur;

    @Enumerated(EnumType.STRING)
    private ConversationStatus status = ConversationStatus.PENDING;

    public static Conversation toEntity(ConversationDto dto){
        return Conversation.builder()
                .status(dto.status)
                .build();
    }

    public static ConversationDto fromEntity(Conversation conversation){

        if(conversation == null) return null;
        return ConversationDto.builder()
                .id(conversation.getId())
                .envoyeur(UtilisateurDto.fromEntity(conversation.getEnvoyeur()))
                .recepteur(UtilisateurDto.fromEntity(conversation.getRecepteur()))
                .status(conversation.getStatus())
                .build();
    }
}
