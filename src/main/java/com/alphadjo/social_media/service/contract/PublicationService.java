package com.alphadjo.social_media.service.contract;

import com.alphadjo.social_media.dto.publication.PublicationDto;

public interface PublicationService extends AbstractService<PublicationDto> {
    PublicationDto savePublication(PublicationDto publicationDto) throws Exception;
}
