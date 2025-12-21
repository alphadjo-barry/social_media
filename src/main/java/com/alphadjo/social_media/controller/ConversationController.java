package com.alphadjo.social_media.controller;

import com.alphadjo.social_media.dto.ConversationDto;
import com.alphadjo.social_media.service.contract.ConversationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@AllArgsConstructor
@ResponseBody
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v1/conversations")
public class ConversationController {

    private final ConversationService conversationService;

    public ResponseEntity<ConversationDto> saveConversation(ConversationDto conversationDto){
        return ResponseEntity.ok(this.conversationService.save(conversationDto));
    }

    public ResponseEntity<Set<ConversationDto>> findByEnvoyeurId(){

        return ResponseEntity.ok(this.conversationService.findByEnvoyeurId());
    }

    public ResponseEntity<Set<ConversationDto>> findByRecepteurId(){
        return ResponseEntity.ok(this.conversationService.findByRecepteurId());
    }
}
