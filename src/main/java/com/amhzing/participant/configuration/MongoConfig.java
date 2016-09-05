package com.amhzing.participant.configuration;

import com.amhzing.participant.annotation.Online;
import com.mongodb.Mongo;
import org.axonframework.eventstore.mongo.DefaultMongoTemplate;
import org.axonframework.eventstore.mongo.MongoTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;

@Configuration
@EnableConfigurationProperties(MongoProperties.class)
@Online
public class MongoConfig {

    @Autowired
    private MongoProperties mongoProperties;

    // This is auto-configured by Spring Boot
    @Autowired
    MongoDbFactory mongoDbFactory;

    @Bean
    MongoTemplate axonMongoTemplate() {
        return new DefaultMongoTemplate(mongo(),
                                        mongoProperties.getDatabase(),
                                        mongoProperties.getEvents(),
                                        mongoProperties.getSnapshots(),
                                        null, null);
    }

    private Mongo mongo() {
        return mongoDbFactory.getDb().getMongo();
    }
}
