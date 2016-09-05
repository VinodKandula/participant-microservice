package com.amhzing.participant.configuration;

import com.amhzing.participant.annotation.Online;
import org.axonframework.contextsupport.spring.AnnotationDriven;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.mongo.MongoEventStore;
import org.axonframework.eventstore.mongo.MongoTemplate;
import org.axonframework.serializer.json.JacksonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@AnnotationDriven
@Online
@Import({MongoConfig.class})
public class AxonConfigOnline {

    @Autowired
    MongoTemplate axonMongoTemplate;

    @Bean
    JacksonSerializer axonJsonSerializer() {
        return new JacksonSerializer();
    }

    @Bean
    EventStore eventStore() {
        return new MongoEventStore(axonJsonSerializer(), axonMongoTemplate);
    }
}
