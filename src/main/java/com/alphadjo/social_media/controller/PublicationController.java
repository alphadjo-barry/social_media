package com.alphadjo.social_media.controller;

import com.alphadjo.social_media.dto.publication.PublicationDto;
import com.alphadjo.social_media.service.contract.PublicationService;

import lombok.AllArgsConstructor;
import okhttp3.Response;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:5173")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/publications")
public class PublicationController {

    private final PublicationService publicationService;

    @GetMapping
    public ResponseEntity<List<PublicationDto>> findAll(){
        return ResponseEntity.ok(publicationService.findAll());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PublicationDto> savePublication(@ModelAttribute PublicationDto dto) throws Exception {
        return ResponseEntity.ok(publicationService.savePublication(dto));
    }
}
