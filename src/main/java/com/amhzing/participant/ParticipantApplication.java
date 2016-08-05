package com.amhzing.participant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ParticipantApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParticipantApplication.class, args);
	}
}
