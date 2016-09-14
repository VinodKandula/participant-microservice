package com.amhzing.participant.query.data;

import java.util.Objects;
import java.util.UUID;

import static org.apache.commons.lang3.Validate.notNull;

public class ParticipantId {

    private final UUID value;

    private ParticipantId(final UUID value) {
        this.value = notNull(value);
    }

    public static ParticipantId create(final UUID value) {
        return new ParticipantId(value);
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ParticipantId that = (ParticipantId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "ParticipantId{" +
                "value='" + value + '\'' +
                '}';
    }
}
