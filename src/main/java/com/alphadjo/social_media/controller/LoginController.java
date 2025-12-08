package com.alphadjo.social_media.controller;

import com.alphadjo.social_media.dto.JwtToken;
import com.alphadjo.social_media.dto.login.LoginRequest;
import com.alphadjo.social_media.service.impl.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:5173/")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/authenticate")
public class LoginController {

    private final JwtService jwtService;

    @PostMapping
        public ResponseEntity<JwtToken> authenticate(@Valid @RequestBody LoginRequest loginRequest){

       String token = jwtService.generateToken(loginRequest.email(), loginRequest.password());

       HttpHeaders headers = new HttpHeaders();
       headers.setBearerAuth(token);
       return new ResponseEntity<>(new JwtToken(token), headers, HttpStatus.OK);
    }
}
