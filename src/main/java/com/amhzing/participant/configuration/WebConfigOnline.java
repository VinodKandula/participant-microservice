package com.amhzing.participant.configuration;

import com.amhzing.participant.annotation.Online;
import com.amhzing.participant.query.data.DefaultQueryParticipant;
import com.amhzing.participant.query.data.QueryParticipant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.CassandraTemplate;

@Online
@Configuration
public class WebConfigOnline {

    // This is auto-configured by Spring Boot
    @Autowired
    CassandraTemplate cassandraTemplate;

    @Bean
    public QueryParticipant queryParticipant() {
        return new DefaultQueryParticipant(cassandraTemplate);
    }
}
