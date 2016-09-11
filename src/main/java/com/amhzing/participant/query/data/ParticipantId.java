package com.amhzing.participant.query.data;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;

public class ParticipantId {

    private final String value;

    private ParticipantId(final String value) {
        this.value = notBlank(value);
    }

    public static ParticipantId create(final String value) {
        return new ParticipantId(value);
    }

    public String getValue() {
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
