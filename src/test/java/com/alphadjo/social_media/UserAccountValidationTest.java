package com.alphadjo.social_media;

import com.alphadjo.social_media.repository.contract.ValidationRepository;
import com.alphadjo.social_media.service.contract.SendMailService;

import com.alphadjo.social_media.service.impl.ValidationServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserAccountValidationTest {

    @Mock
    private ValidationRepository validationRepository;

    @Mock
    private SendMailService sendMailService;

    @InjectMocks
    private ValidationServiceImpl validationServiceImpl;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void sendValidationTest(){

    }
}
