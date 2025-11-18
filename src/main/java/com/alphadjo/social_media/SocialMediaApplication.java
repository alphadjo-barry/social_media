package com.alphadjo.social_media;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@AllArgsConstructor
public class SocialMediaApplication{

	public static void main(String[] args) {
		SpringApplication.run(SocialMediaApplication.class, args);
	}
}
