package com.amhzing.participant.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@JsonInclude
public class CreateParticipantResponse {

    @NotNull
    @JsonProperty("participantId")
    private final String participantId;

    @NotNull
    @JsonProperty("errors")
    private final List<ResponseError> errors;

    private CreateParticipantResponse(final String participantId, final List<ResponseError> errors) {
        this.participantId = participantId;
        this.errors = errors;
    }

    public static CreateParticipantResponse create(final String participantId, final List<ResponseError> errors) {
        return new CreateParticipantResponse(participantId, errors);
    }

    public String getParticipantId() {
        return participantId;
    }

    public List<ResponseError> getErrors() {
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CreateParticipantResponse response = (CreateParticipantResponse) o;
        return Objects.equals(participantId, response.participantId) &&
                Objects.equals(errors, response.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participantId, errors);
    }

    @Override
    public String toString() {
        return "CreateParticipantResponse{" +
                "participantId='" + participantId + '\'' +
                ", errors=" + errors +
                '}';
    }
}
