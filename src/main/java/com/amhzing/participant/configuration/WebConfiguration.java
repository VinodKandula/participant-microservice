package com.amhzing.participant.configuration;

import com.amhzing.participant.application.command.CreateParticipantService;
import com.amhzing.participant.application.command.DefaultCreateParticipantService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {

    @Bean
    public CreateParticipantService createParticipantService() {
        return new DefaultCreateParticipantService();
    }
}
