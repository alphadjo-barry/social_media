package com.alphadjo.social_media.dto.reaction;

import com.alphadjo.social_media.dto.utilisateur.UtilisateurDto;
import com.alphadjo.social_media.entity.Reaction;
import com.alphadjo.social_media.enums.TypeReaction;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReactionDto {

    private Long id;
    private TypeReaction type = TypeReaction.LIKE;

    @NotNull(message = "Publication is required")

    private Long publicationId;

    @NotNull(message = "Utilisateur is required")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long utilisateurId;

    private UtilisateurDto utilisateur;

    public static Reaction toEntity(ReactionDto reactionDto) {

        if(reactionDto == null) return null;

        return Reaction.builder()
                .type(TypeReaction.LIKE)
                .build();
    }

    public static ReactionDto fromEntity(Reaction reaction) {
        if(reaction == null) return null;

        return ReactionDto.builder()
                .id(reaction.getId())
                .publicationId(reaction.getPublication().getId())
                .type(reaction.getType())
                .utilisateur(UtilisateurDto.fromEntity(reaction.getUtilisateur()))
                .build();
    }
}
