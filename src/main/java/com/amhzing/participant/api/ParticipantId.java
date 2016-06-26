package com.amhzing.participant.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

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

    public String getValue() {
        return value;
    }
}
