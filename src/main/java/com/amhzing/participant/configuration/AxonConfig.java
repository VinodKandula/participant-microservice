package com.amhzing.participant.configuration;

import com.amhzing.participant.command.domain.Participant;
import com.amhzing.participant.command.domain.gateway.MetaDataEnrichedCommandGateway;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.commandhandling.gateway.GatewayProxyFactory;
import org.axonframework.contextsupport.spring.AnnotationDriven;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@AnnotationDriven
@Import({AxonConfigOnline.class, AxonConfigOffline.class})
public class AxonConfig {

    @Autowired
    private EventStore eventStore;

    // Consider using the Disruptor instead
    @Bean
    public SimpleCommandBus commandBus() {
        return new SimpleCommandBus();
    }

    @Bean
    public MetaDataEnrichedCommandGateway metaDataEnrichedCommandGateway() {
        GatewayProxyFactory factory = new GatewayProxyFactory(commandBus());

        return factory.createGateway(MetaDataEnrichedCommandGateway.class);
    }

    @Bean
    public SimpleEventBus eventBus() {
        return new SimpleEventBus();
    }

    @Bean
    public EventSourcingRepository<Participant> participantEventSourcingRepository() {
        EventSourcingRepository<Participant> repository = new EventSourcingRepository<>(Participant.class, eventStore);
        repository.setEventBus(eventBus());
        return repository;
    }

    // Allows Axon to automatically find @EventHandler's
    @Bean
    public AnnotationEventListenerBeanPostProcessor eventListenerBeanPostProcessor() {
        AnnotationEventListenerBeanPostProcessor proc = new AnnotationEventListenerBeanPostProcessor();
        proc.setEventBus(eventBus());
        return proc;
    }

     // Allows Axon to automatically find @CommandHandler's
    @Bean
    public AnnotationCommandHandlerBeanPostProcessor commandHandlerBeanPostProcessor() {
        AnnotationCommandHandlerBeanPostProcessor proc = new AnnotationCommandHandlerBeanPostProcessor();
        proc.setCommandBus(commandBus());
        return proc;
    }

     // Registers the Aggregate Root as a @CommandHandler
    @Bean
    public AggregateAnnotationCommandHandler<Participant> participantAggregateCommandHandler() {
        AggregateAnnotationCommandHandler<Participant> handler =
                new AggregateAnnotationCommandHandler<>(Participant.class, participantEventSourcingRepository());

        handler.supportedCommands().stream().forEach(command -> commandBus().subscribe(command, handler));

        return handler;
    }
}
