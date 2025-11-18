package com.alphadjo.social_media.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PublicationPicture extends AbstractEntity{

    private String photoOriginalName;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String picturePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publication_id")
    private Publication publication;
}
