package com.amhzing.participant.command.application;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public class CreatedParticipant {

    private final String participantId;

    private CreatedParticipant(final String participantId) {
        this.participantId = notNull(participantId);
    }

    public static CreatedParticipant create(final String participantId) {
        return new CreatedParticipant(participantId);
    }

    public static CreatedParticipant empty() {
        return CreatedParticipant.create("");
    }

    public String getParticipantId() {
        return participantId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CreatedParticipant that = (CreatedParticipant) o;
        return Objects.equals(participantId, that.participantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participantId);
    }

    @Override
    public String toString() {
        return "CreatedParticipant{" +
                "participantId='" + participantId + '\'' +
                '}';
    }
}
