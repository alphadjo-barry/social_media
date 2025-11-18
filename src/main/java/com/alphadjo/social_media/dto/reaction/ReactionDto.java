package com.alphadjo.social_media.dto.reaction;

import com.alphadjo.social_media.dto.utilisateur.UtilisateurDto;
import com.alphadjo.social_media.entity.Reaction;
import com.alphadjo.social_media.enums.TypeReaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReactionDto {

    private Long id;
    private TypeReaction type;
    private Long publicationId;
    private UtilisateurDto utilisateur;

    public static Reaction toEntity(ReactionDto reactionDto) {

        if(reactionDto == null) return null;

        return Reaction.builder()
                .type(reactionDto.getType())
                .utilisateur(UtilisateurDto.toEntity(reactionDto.getUtilisateur()))
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
