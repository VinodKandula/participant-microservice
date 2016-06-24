package com.amhzing.participant.model;

import com.amhzing.participant.api.command.CreateParticipantCommand;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class Participant extends AbstractAnnotatedAggregateRoot {

    private static final Logger LOG = LoggerFactory.getLogger(Participant.class);

    @AggregateIdentifier
    private UUID id;

    private Name name;

    // no-arg constructor for Axon
    public Participant() {
    }

    @CommandHandler
    public Participant(final CreateParticipantCommand createParticipantCommand) {
        LOG.debug("Command: 'CreateParticipantCommand' received.");
    }
}
