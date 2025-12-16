package com.alphadjo.social_media.service.contract;


import com.alphadjo.social_media.dto.commentaire.CommentaireDto;

import java.util.List;

public interface CommentaireService extends AbstractService<CommentaireDto>{

    List<CommentaireDto> findByPublicationId(Long publicationId);
}
