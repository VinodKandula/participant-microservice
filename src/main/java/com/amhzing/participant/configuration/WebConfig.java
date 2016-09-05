package com.amhzing.participant.configuration;

import com.amhzing.participant.command.application.CreateParticipantService;
import com.amhzing.participant.command.application.DefaultCreateParticipantService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({WebConfigOnline.class, WebConfigOffline.class})
public class WebConfig {

    @Bean
    public CreateParticipantService createParticipantService() {
        return new DefaultCreateParticipantService();
    }
}
