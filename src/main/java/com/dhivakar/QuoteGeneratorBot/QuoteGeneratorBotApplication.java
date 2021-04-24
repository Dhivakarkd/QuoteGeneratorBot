package com.dhivakar.QuoteGeneratorBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QuoteGeneratorBotApplication {

	public static void main(String[] args) {

		SpringApplication.run(QuoteGeneratorBotApplication.class, args);
	}

}
