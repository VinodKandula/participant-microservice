package com.amhzing.participant.configuration;

import com.amhzing.participant.gateway.MetaDataEnrichedCommandGateway;
import com.amhzing.participant.model.Participant;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.commandhandling.gateway.GatewayProxyFactory;
import org.axonframework.contextsupport.spring.AnnotationDriven;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.mongo.MongoEventStore;
import org.axonframework.eventstore.mongo.MongoTemplate;
import org.axonframework.serializer.json.JacksonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AnnotationDriven
public class AxonConfiguration {

    @Autowired
    MongoTemplate axonMongoTemplate;

    @Bean
    SimpleCommandBus commandBus() {
        return new SimpleCommandBus();
    }

    @Bean
    MetaDataEnrichedCommandGateway metaDataEnrichedCommandGateway() {
        GatewayProxyFactory factory = new GatewayProxyFactory(commandBus());

        return factory.createGateway(MetaDataEnrichedCommandGateway.class);
    }

    // This will need to be replaced with a cluster event bus later
    @Bean
    SimpleEventBus eventBus() {
        return new SimpleEventBus();
    }

    @Bean
    JacksonSerializer axonJsonSerializer() {
        return new JacksonSerializer();
    }

    @Bean
    EventStore eventStore() {
        return new MongoEventStore(axonJsonSerializer(), axonMongoTemplate);
    }

    @Bean
    EventSourcingRepository<Participant> participantEventSourcingRepository() {
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
}
