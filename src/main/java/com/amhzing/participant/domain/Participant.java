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

import java.util.Objects;
import java.util.UUID;

import static com.amhzing.participant.domain.ParticipantFactory.*;

public class Participant extends AbstractAnnotatedAggregateRoot {

    private static final Logger LOGGER = LoggerFactory.getLogger(Participant.class);

    @AggregateIdentifier
    private UUID id;

    @EventSourcedMember
    private Name name;
    @EventSourcedMember
    private Address address;
    @EventSourcedMember
    private ContactNumber contactNumber;
    @EventSourcedMember
    private Email email;

    // no-arg constructor for Axon
    public Participant() {
    }

    @CommandHandler
    public Participant(final CreateParticipantCommand command, final MetaData metadata) {
        LOGGER.info("Applying {} for participant {}", ParticipantCreatedEvent.class.getSimpleName(), command.getId());
        apply(ParticipantCreatedEvent.create(command.getId(),
                                             command.getName(),
                                             command.getAddress(),
                                             command.getContactNumber(),
                                             command.getEmail()),
              metadata);
    }

    @EventSourcingHandler
    public void handleEvent(final ParticipantCreatedEvent event, final MetaData metadata) {
        LOGGER.info("Handling {} for participant {}", ParticipantCreatedEvent.class.getSimpleName(), event.getId());
        this.id = event.getId();
        this.name = createNameFrom(event);
        this.address = createAddressFrom(event);
        this.contactNumber = createContactNumberFrom(event);
        this.email = createEmailFrom(event);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Participant that = (Participant) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address) &&
                Objects.equals(contactNumber, that.contactNumber) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, contactNumber, email);
    }
}
