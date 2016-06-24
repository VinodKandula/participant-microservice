package com.amhzing.participant.api.command;

import com.amhzing.participant.api.Name;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import java.util.UUID;

public class CreateParticipantCommand {

    @TargetAggregateIdentifier
    private final UUID id;
    private final Name name;

    public CreateParticipantCommand(final UUID id, final Name name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public Name getName() {
        return name;
    }
}
