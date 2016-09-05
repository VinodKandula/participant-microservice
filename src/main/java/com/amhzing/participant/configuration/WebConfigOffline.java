package com.amhzing.participant.configuration;

import com.amhzing.participant.annotation.Offline;
import com.amhzing.participant.query.data.InMemQueryParticipant;
import com.amhzing.participant.query.data.QueryParticipant;
import com.amhzing.participant.query.data.jpa.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Offline
public class WebConfigOffline {

    @Autowired
    ParticipantRepository participantRepository;

    @Bean
    public QueryParticipant queryParticipant() {
        return new InMemQueryParticipant(participantRepository);
    }
}
