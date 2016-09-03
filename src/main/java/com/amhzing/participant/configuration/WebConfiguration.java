package com.amhzing.participant.configuration;

import com.amhzing.participant.command.application.CreateParticipantService;
import com.amhzing.participant.command.application.DefaultCreateParticipantService;
import com.amhzing.participant.query.data.DefaultQueryParticipant;
import com.amhzing.participant.query.data.QueryParticipant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.CassandraTemplate;

@Configuration
public class WebConfiguration {

    // This is auto-configured by Spring Boot
    @Autowired
    CassandraTemplate cassandraTemplate;

    @Bean
    public CreateParticipantService createParticipantService() {
        return new DefaultCreateParticipantService();
    }

    @Bean
    public QueryParticipant queryParticipant() {
        return new DefaultQueryParticipant(cassandraTemplate);
    }
}
