package com.amhzing.participant.web.response;

import com.amhzing.participant.api.model.ParticipantInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@JsonInclude
public class QueryParticipantResponse {

    @NotNull
    @JsonProperty("participants")
    private final List<ParticipantInfo> participants;

    @NotNull
    @JsonProperty("error")
    private final ResponseError error;

    private QueryParticipantResponse(final List<ParticipantInfo> participants, final ResponseError error) {
        this.participants = participants;
        this.error = error;
    }

    public static QueryParticipantResponse create(final List<ParticipantInfo> participants, final ResponseError error) {
        return new QueryParticipantResponse(participants, error);
    }

    public List<ParticipantInfo> getParticipants() {
        return participants;
    }

    public ResponseError getError() {
        return error;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final QueryParticipantResponse that = (QueryParticipantResponse) o;
        return Objects.equals(participants, that.participants) &&
                Objects.equals(error, that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participants, error);
    }

    @Override
    public String toString() {
        return "QueryParticipantResponse{" +
                "participants=" + participants +
                ", error=" + error +
                '}';
    }
}
