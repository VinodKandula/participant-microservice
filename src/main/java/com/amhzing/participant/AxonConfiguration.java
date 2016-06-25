package com.amhzing.participant;

import com.amhzing.participant.model.Participant;
import com.google.common.collect.ImmutableList;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.contextsupport.spring.AnnotationDriven;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.mongo.DefaultMongoTemplate;
import org.axonframework.eventstore.mongo.MongoEventStore;
import org.axonframework.eventstore.mongo.MongoFactory;
import org.axonframework.eventstore.mongo.MongoTemplate;
import org.axonframework.serializer.json.JacksonSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

@Configuration
@AnnotationDriven
public class AxonConfiguration {

    @Bean
    public SimpleCommandBus commandBus() {
        return new SimpleCommandBus();
    }

    @Bean
    public DefaultCommandGateway commandGateway() {
        return new DefaultCommandGateway(commandBus());
    }

    // This will need to be replaced with a cluster event bus later
    @Bean
    public SimpleEventBus eventBus() {
        return new SimpleEventBus();
    }

    @Bean
    JacksonSerializer axonJsonSerializer() {
        return new JacksonSerializer();
    }

    @Bean(name = "axonMongoTemplate")
    public MongoTemplate axonMongoTemplate() {
        return new DefaultMongoTemplate(mongo(),
                                        "activitiesDB", "participantEvents", "participantSnapshots",
                                        null, null);
    }

    @Bean
    public EventStore eventStore() {
        return new MongoEventStore(axonJsonSerializer(), axonMongoTemplate());
    }

    @Bean
    public EventSourcingRepository<Participant> participantEventSourcingRepository() {
        EventSourcingRepository<Participant> repository = new EventSourcingRepository<>(Participant.class, eventStore());
        repository.setEventBus(eventBus());
        return repository;
    }

    // Allows Axon to automatically find @EventHandler's
    @Bean
    AnnotationEventListenerBeanPostProcessor eventListenerBeanPostProcessor() {
        AnnotationEventListenerBeanPostProcessor proc = new AnnotationEventListenerBeanPostProcessor();
        proc.setEventBus(eventBus());
        return proc;
    }

     // Allows Axon to automatically find @CommandHandler's
    @Bean
    AnnotationCommandHandlerBeanPostProcessor commandHandlerBeanPostProcessor() {
        AnnotationCommandHandlerBeanPostProcessor proc = new AnnotationCommandHandlerBeanPostProcessor();
        proc.setCommandBus(commandBus());
        return proc;
    }

     // Registers the Aggregate Root as a @CommandHandler
    @Bean
    AggregateAnnotationCommandHandler<Participant> participantAggregateCommandHandler() {
        AggregateAnnotationCommandHandler<Participant> handler =
                new AggregateAnnotationCommandHandler<>(Participant.class, participantEventSourcingRepository());

        handler.supportedCommands().stream().forEach(command -> commandBus().subscribe(command, handler));

        return handler;
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
