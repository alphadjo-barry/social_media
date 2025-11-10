package com.alphadjo.social_media.controller;

import com.alphadjo.social_media.dto.error.ErrorEntity;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorEntity> handleException(RuntimeException exception){
        ErrorEntity error = ErrorEntity.builder()
                .code("401")
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(401).body(error);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorEntity> handleException(EntityExistsException exception){
        ErrorEntity error = ErrorEntity.builder()
                .code("401")
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(401).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorEntity> handleException(IllegalArgumentException exception){
        ErrorEntity error = ErrorEntity.builder()
                .code("400")
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorEntity> handleException(EntityNotFoundException exception){
        ErrorEntity error = ErrorEntity.builder()
                .code("404")
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
