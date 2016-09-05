package com.amhzing.participant.configuration;

import com.amhzing.participant.annotation.Offline;
import org.axonframework.contextsupport.spring.AnnotationDriven;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.fs.FileSystemEventStore;
import org.axonframework.eventstore.fs.SimpleEventFileResolver;
import org.axonframework.serializer.json.JacksonSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@AnnotationDriven
@Offline
public class AxonConfigOffline {

    @Bean
    JacksonSerializer axonJsonSerializer() {
        return new JacksonSerializer();
    }

    @Bean
    public EventStore eventStore() {
        return new FileSystemEventStore(axonJsonSerializer(),
                                        new SimpleEventFileResolver(new File("./participantEvents")));
    }
}
