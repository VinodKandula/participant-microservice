package com.amhzing.participant.configuration;

import com.amhzing.participant.annotation.Offline;
import com.amhzing.participant.query.data.InMemQueryParticipant;
import com.amhzing.participant.query.data.QueryParticipant;
import com.amhzing.participant.query.data.jpa.repository.ParticipantQueryDslRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Offline
@Configuration
public class WebConfigOffline {

    @Autowired
    private ParticipantQueryDslRepository participantRepository;

    @Bean
    public QueryParticipant queryParticipant() {
        return new InMemQueryParticipant(participantRepository);
    }
}
