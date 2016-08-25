package com.amhzing.participant.configuration;

import com.google.common.collect.ImmutableList;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;
import org.axonframework.eventstore.mongo.DefaultMongoTemplate;
import org.axonframework.eventstore.mongo.MongoFactory;
import org.axonframework.eventstore.mongo.MongoTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MongoProperties.class)
public class MongoConfiguration {

    @Autowired
    private MongoProperties mongoProperties;

    @Bean
    MongoTemplate axonMongoTemplate() {
        return new DefaultMongoTemplate(mongo(),
                                        mongoProperties.getDatabase(),
                                        mongoProperties.getEvents(),
                                        mongoProperties.getSnapshots(),
                                        null, null);
    }

    private Mongo mongo() {
        final MongoFactory mongoFactory = new MongoFactory();
        final ServerAddress serverAddress;
        try {
            serverAddress = new ServerAddress(mongoProperties.getHost(), mongoProperties.getPort());
        } catch (Exception ex) {
            throw new RuntimeException("Mongo DB  Initialization ResponseError ", ex);
        }
        mongoFactory.setMongoAddresses(ImmutableList.of(serverAddress));
        final Mongo mongo = mongoFactory.createMongo();
        return mongo;
    }
}
