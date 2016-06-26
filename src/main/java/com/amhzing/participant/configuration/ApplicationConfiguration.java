package com.amhzing.participant.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MongoConfiguration.class, AxonConfiguration.class})
public class ApplicationConfiguration {
}
