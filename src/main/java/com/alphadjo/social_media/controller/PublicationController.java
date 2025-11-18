package com.alphadjo.social_media.controller;

import com.alphadjo.social_media.dto.publication.PublicationDto;
import com.alphadjo.social_media.service.contract.PublicationService;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/publications")
public class PublicationController {

    private final PublicationService publicationService;

    @GetMapping
    public  ResponseEntity<List<PublicationDto>> index(){
        return ResponseEntity.ok(publicationService.findAll());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PublicationDto> savePublication(@ModelAttribute PublicationDto dto) throws Exception {

        return ResponseEntity.ok(publicationService.savePublication(dto));
    }
}
