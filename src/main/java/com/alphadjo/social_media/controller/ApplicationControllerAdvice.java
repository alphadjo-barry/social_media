package com.alphadjo.social_media.controller;

import com.alphadjo.social_media.dto.error.ErrorEntity;
import com.alphadjo.social_media.exceptions.FileStorageException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ErrorEntity> handleException(FileStorageException exception, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorEntity(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorEntity> handleException(RuntimeException exception,  HttpServletRequest request){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorEntity(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorEntity> handleException(EntityExistsException exception, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorEntity(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorEntity> handleException(IllegalArgumentException exception, HttpServletRequest request){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorEntity(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorEntity> handleException(EntityNotFoundException exception, HttpServletRequest request){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorEntity(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorEntity> handleException(DisabledException exception, HttpServletRequest request){

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorEntity(
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request){
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
