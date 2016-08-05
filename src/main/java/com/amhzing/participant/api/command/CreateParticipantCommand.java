package com.amhzing.participant.api.command;

import com.amhzing.participant.api.Name;

import java.util.UUID;

public class CreateParticipantCommand extends AbstractParticipantCommand {

    private final Name name;

    public CreateParticipantCommand(final UUID id, final Name name) {
        super(id);
        this.name = name;
    }

    public Name getName() {
        return name;
    }
}
