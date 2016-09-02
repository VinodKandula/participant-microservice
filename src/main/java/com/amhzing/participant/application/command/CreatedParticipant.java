package com.amhzing.participant.application.command;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public class CreatedParticipant {

    private final String participantId;
    private final String correlationId;

    private CreatedParticipant(final String participantId, final String correlationId) {
        this.participantId = notNull(participantId);
        this.correlationId = notNull(correlationId);
    }

    public static CreatedParticipant create(final String participantId, final String correlationId) {
        return new CreatedParticipant(participantId, correlationId);
    }

    public static CreatedParticipant empty() {
        return CreatedParticipant.create("", "");
    }

    public String getParticipantId() {
        return participantId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CreatedParticipant that = (CreatedParticipant) o;
        return Objects.equals(participantId, that.participantId) &&
                Objects.equals(correlationId, that.correlationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participantId, correlationId);
    }

    @Override
    public String toString() {
        return "CreatedParticipant{" +
                "participantId='" + participantId + '\'' +
                ", correlationId='" + correlationId + '\'' +
                '}';
    }
}
