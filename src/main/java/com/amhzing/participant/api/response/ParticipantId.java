package com.amhzing.participant.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@JsonInclude
public class ParticipantId {

    @NotNull
    @JsonProperty("participantId")
    private final String value;

    private ParticipantId(final String value) {
        this.value = value;
    }

    public static ParticipantId create(final String value) {
        return new ParticipantId(value);
    }

    public static ParticipantId empty() {
        return new ParticipantId("");
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
