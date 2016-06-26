package com.amhzing.participant;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MongoConfiguration.class, AxonConfiguration.class})
public class ApplicationConfiguration {
}
