package com.amhzing.participant.api.event;

import java.util.UUID;

public abstract class AbstractParticipantEvent {

    private UUID id;

    public AbstractParticipantEvent() {
    }

    public AbstractParticipantEvent(final UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
