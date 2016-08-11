package com.amhzing.participant.api.event;

import java.util.UUID;

import static org.apache.commons.lang3.Validate.notNull;

public abstract class AbstractParticipantEvent {

    private UUID id;

    public AbstractParticipantEvent() {
    }

    public AbstractParticipantEvent(final UUID id) {
        this.id = notNull(id);
    }

    public UUID getId() {
        return id;
    }
}
