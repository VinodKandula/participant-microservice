package com.amhzing.participant.api.event;

import java.io.Serializable;
import java.util.UUID;

import static org.apache.commons.lang3.Validate.notNull;

public abstract class AbstractParticipantEvent implements Serializable {

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
