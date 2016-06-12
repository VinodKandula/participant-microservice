package com.amhzing.participant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ParticipantApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParticipantApplication.class, args);
	}

	@RequestMapping("/mahan")
	String home() {
		return "Hello World!";
	}
}
