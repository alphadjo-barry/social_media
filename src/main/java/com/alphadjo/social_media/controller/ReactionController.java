package com.alphadjo.social_media.controller;

import com.alphadjo.social_media.dto.reaction.ReactionDto;
import com.alphadjo.social_media.service.contract.ReactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/reactions")
public class ReactionController {

    private final ReactionService reactionService;

    @PostMapping
    public ResponseEntity<ReactionDto> save(@RequestBody @Valid ReactionDto reactionDto){

        return ResponseEntity.ok(reactionService.save(reactionDto));
    }
}
