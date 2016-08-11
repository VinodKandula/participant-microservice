package com.amhzing.participant.api.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import java.util.UUID;

import static org.apache.commons.lang3.Validate.notNull;

public abstract class AbstractParticipantCommand {

    @TargetAggregateIdentifier
    private UUID id;

    public AbstractParticipantCommand() {
    }

    public AbstractParticipantCommand(final UUID id) {
        this.id = notNull(id);
    }

    public UUID getId() {
        return id;
    }
}
