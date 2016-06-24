package com.amhzing.participant.api.event;

import com.amhzing.participant.api.Name;

import java.util.UUID;

public class ParticipantCreatedEvent extends AbstractParticipantEvent {

    private Name name;

    public ParticipantCreatedEvent() {
    }

    public ParticipantCreatedEvent(final UUID id, final Name name) {
        super(id);
        this.name = name;
    }

    public Name getName() {
        return name;
    }
}
