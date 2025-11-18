package com.alphadjo.social_media.dto.picture;

import com.alphadjo.social_media.dto.publication.PublicationDto;
import com.alphadjo.social_media.entity.PublicationPicture;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublicationPictureDto {

    private Long id;

    private String photoOriginalName;

    @Column(columnDefinition = "TEXT")
    private String picturePath;

    private Long publicationId;

    public static PublicationPicture toEntity(PublicationPictureDto publicationPictureDto) {

        if(publicationPictureDto == null) return null;

        return PublicationPicture.builder()
                .photoOriginalName(publicationPictureDto.getPhotoOriginalName())
                .picturePath(publicationPictureDto.getPicturePath())
                .build();
    }

    public static PublicationPictureDto fromEntity(PublicationPicture publicationPicture) {

        if(publicationPicture == null) return null;

        return PublicationPictureDto.builder()
                .photoOriginalName(publicationPicture.getPhotoOriginalName())
                .picturePath(publicationPicture.getPicturePath())
                .publicationId(publicationPicture.getPublication().getId())
                .build();
    }
}
