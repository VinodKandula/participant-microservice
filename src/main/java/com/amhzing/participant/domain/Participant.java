package com.amhzing.participant.domain;

import com.amhzing.participant.api.command.CreateParticipantCommand;
import com.amhzing.participant.api.event.ParticipantCreatedEvent;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.domain.MetaData;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcedMember;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static net.logstash.logback.encoder.org.apache.commons.lang.StringUtils.isNotEmpty;

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
    public Participant(final CreateParticipantCommand command, final MetaData metadata) {
        System.out.println("Command: 'CreateParticipantCommand' received.");
        System.out.println("Queuing up a new ParticipantCreatedEvent for participant " + command.getId());
        apply(new ParticipantCreatedEvent(command.getId(), command.getName()), metadata);
    }

    @EventSourcingHandler
    public void on(final ParticipantCreatedEvent event, final MetaData metadata) {
        this.id = event.getId();
        this.name = createNameFrom(event);
        System.out.println("Applied: ProductAddedEvent" + event.getId() + ", " + event.getName());
    }

    private Name createNameFrom(final ParticipantCreatedEvent event) {
        final String firstName = event.getName().getFirstName();
        final String middleName = event.getName().getMiddleName();
        final String lastName = event.getName().getLastName();
        final String suffix = event.getName().getSuffix();

        return Name.create(isNotEmpty(firstName) ? FirstName.create(firstName) : null,
                           isNotEmpty(middleName) ? MiddleName.create(middleName) : null,
                           isNotEmpty(lastName) ? LastName.create(lastName) : null,
                           isNotEmpty(suffix) ? Suffix.create(suffix) : null);
    }
}
