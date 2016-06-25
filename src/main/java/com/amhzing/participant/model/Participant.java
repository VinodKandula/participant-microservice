package com.amhzing.participant.model;

import com.amhzing.participant.api.command.CreateParticipantCommand;
import com.amhzing.participant.api.event.ParticipantCreatedEvent;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcedMember;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class Participant extends AbstractAnnotatedAggregateRoot {

    private static final Logger LOGGER = LoggerFactory.getLogger(Participant.class);

    @AggregateIdentifier
    private UUID id;

    @EventSourcedMember
    private Name name;

    // no-arg constructor for Axon
    public Participant() {
    }

    @CommandHandler
    public Participant(final CreateParticipantCommand command) {
        System.out.println("Command: 'CreateParticipantCommand' received.");
        System.out.println("Queuing up a new ParticipantCreatedEvent for participant " + command.getId());
        apply(new ParticipantCreatedEvent(command.getId(), command.getName()));
    }

    @EventSourcingHandler
    public void onEvent(ParticipantCreatedEvent event) {
        this.id = event.getId();
        this.name = Name.create(FirstName.create(event.getName().getFirstName()),
                                MiddleName.create(event.getName().getMiddleName()),
                                LastName.create(event.getName().getLastName()),
                                Suffix.create(event.getName().getSuffix()));
        System.out.println("Applied: ProductAddedEvent" + event.getId() + ", " + event.getName());
    }
}
