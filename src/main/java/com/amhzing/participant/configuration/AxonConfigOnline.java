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

@Online
@Configuration
@AnnotationDriven
@Import({MongoConfig.class})
public class AxonConfigOnline {

    @Autowired
    private MongoTemplate axonMongoTemplate;

    @Bean
    public JacksonSerializer axonJsonSerializer() {
        return new JacksonSerializer();
    }

    @Bean
    public EventStore eventStore() {
        return new MongoEventStore(axonJsonSerializer(), axonMongoTemplate);
    }
}
