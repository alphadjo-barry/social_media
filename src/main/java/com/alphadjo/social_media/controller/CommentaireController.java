package com.alphadjo.social_media.controller;

import com.alphadjo.social_media.dto.commentaire.CommentaireDto;
import com.alphadjo.social_media.service.contract.CommentaireService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:5173")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/commentaires")
public class CommentaireController {

    private  final CommentaireService commentaireService;

    @PostMapping
    public void saveCommentaire(@Valid @RequestBody CommentaireDto commentaireDto){
        this.commentaireService.save(commentaireDto);
    }

    @GetMapping("/publication/{publicationId}")
    public List<CommentaireDto> findByPublicationId(@PathVariable Long publicationId){
        return commentaireService.findByPublicationId(publicationId);
    }

}
