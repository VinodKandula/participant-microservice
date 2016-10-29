package com.amhzing.participant.configuration;

import com.amhzing.participant.command.application.CreateParticipantService;
import com.amhzing.participant.command.application.DefaultCreateParticipantService;
import com.amhzing.participant.command.domain.gateway.MetaDataEnrichedCommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({WebConfigOnline.class, WebConfigOffline.class})
public class WebConfig {

    @Autowired
    private MetaDataEnrichedCommandGateway commandGateway;

    @Bean
    public CreateParticipantService createParticipantService() {
        return new DefaultCreateParticipantService(commandGateway);
    }
}
