package com.alphadjo.social_media.controller;

import com.alphadjo.social_media.dto.ConversationDto;
import com.alphadjo.social_media.service.contract.ConversationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@AllArgsConstructor
@ResponseBody
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v1/conversations")
public class ConversationController {

    private final ConversationService conversationService;

    @PostMapping("/requests")
    public ResponseEntity<ConversationDto> saveConversation(@RequestBody @Valid ConversationDto conversationDto){
        return ResponseEntity.ok(this.conversationService.save(conversationDto));
    }

    @GetMapping("/sent")
    public ResponseEntity<Set<ConversationDto>> findByEnvoyeurId(){

        return ResponseEntity.ok(this.conversationService.findByEnvoyeurId());
    }

    @GetMapping("/receive")
    public ResponseEntity<Set<ConversationDto>> findByRecepteurId(){
        return ResponseEntity.ok(this.conversationService.findByRecepteurId());
    }
}
