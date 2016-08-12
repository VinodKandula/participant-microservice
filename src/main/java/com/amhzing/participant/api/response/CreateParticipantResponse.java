package com.amhzing.participant.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@JsonInclude
public class CreateParticipantResponse {

    @NotNull
    @JsonProperty("participantId")
    private final String participantId;
    @NotNull
    @JsonProperty("error")
    private final ResponseError error;

    private CreateParticipantResponse(final String participantId, final ResponseError error) {
        this.participantId = participantId;
        this.error = error;
    }

    public static CreateParticipantResponse create(final String participantId, final ResponseError error) {
        return new CreateParticipantResponse(participantId, error);
    }

    public String getParticipantId() {
        return participantId;
    }

    public ResponseError getError() {
        return error;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CreateParticipantResponse that = (CreateParticipantResponse) o;
        return Objects.equals(participantId, that.participantId) &&
                Objects.equals(error, that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participantId, error);
    }

    @Override
    public String toString() {
        return "CreateParticipantResponse{" +
                "participantId='" + participantId + '\'' +
                ", error=" + error +
                '}';
    }
}
