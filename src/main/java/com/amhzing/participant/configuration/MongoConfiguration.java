package com.amhzing.participant.configuration;

import com.google.common.collect.ImmutableList;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;
import org.axonframework.eventstore.mongo.DefaultMongoTemplate;
import org.axonframework.eventstore.mongo.MongoFactory;
import org.axonframework.eventstore.mongo.MongoTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

@Configuration
public class MongoConfiguration {

    @Bean
    MongoTemplate axonMongoTemplate() {
        return new DefaultMongoTemplate(mongo(),
                                        "activitiesDB", "participantEvents", "participantSnapshots",
                                        null, null);
    }

    private Mongo mongo() {
        final MongoFactory mongoFactory = new MongoFactory();
        final ServerAddress serverAddress;
        try {
            serverAddress = new ServerAddress("192.168.1.32", 27017);
        } catch (UnknownHostException unknownHostEx) {
            throw new RuntimeException("Mongo DB  Initialization Error ", unknownHostEx);
        }
        mongoFactory.setMongoAddresses(ImmutableList.of(serverAddress));
        final Mongo mongo = mongoFactory.createMongo();
        return mongo;
    }
}
