package com.amhzing.participant.configuration;

import com.google.common.collect.ImmutableList;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;
import org.axonframework.eventstore.mongo.DefaultMongoTemplate;
import org.axonframework.eventstore.mongo.MongoFactory;
import org.axonframework.eventstore.mongo.MongoTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfiguration {

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private int port;

    @Value("${spring.data.mongodb.events}")
    private String eventsCollection;

    @Value("${spring.data.mongodb.snapshots}")
    private String snapshotCollection;

    @Bean
    MongoTemplate axonMongoTemplate() {
        return new DefaultMongoTemplate(mongo(),
                                        database, eventsCollection, snapshotCollection,
                                        null, null);
    }

    private Mongo mongo() {
        final MongoFactory mongoFactory = new MongoFactory();
        final ServerAddress serverAddress;
        try {
            serverAddress = new ServerAddress(host, port);
        } catch (Exception ex) {
            throw new RuntimeException("Mongo DB  Initialization ResponseError ", ex);
        }
        mongoFactory.setMongoAddresses(ImmutableList.of(serverAddress));
        final Mongo mongo = mongoFactory.createMongo();
        return mongo;
    }
}
