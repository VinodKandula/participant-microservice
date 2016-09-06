package com.amhzing.participant.configuration;

import com.amhzing.participant.annotation.Offline;
import com.amhzing.participant.query.data.jpa.repository.ParticipantQueryDslRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Offline
@Configuration
@EnableJpaRepositories(basePackageClasses = {ParticipantQueryDslRepository.class})
public class H2Config {
}
