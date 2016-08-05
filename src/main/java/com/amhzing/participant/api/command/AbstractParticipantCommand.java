package com.amhzing.participant.api.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import java.util.UUID;

public abstract class AbstractParticipantCommand {

    @TargetAggregateIdentifier
    private UUID id;

    public AbstractParticipantCommand() {
    }

    public AbstractParticipantCommand(final UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
