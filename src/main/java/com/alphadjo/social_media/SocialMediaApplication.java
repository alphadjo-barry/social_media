package com.alphadjo.social_media;

import lombok.AllArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SocialMediaApplication{

	public static void main(String[] args) {
		SpringApplication.run(SocialMediaApplication.class, args);
	}
}