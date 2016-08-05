package com.amhzing.participant.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

@JsonInclude
public class ParticipantId {

    @NotBlank
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
