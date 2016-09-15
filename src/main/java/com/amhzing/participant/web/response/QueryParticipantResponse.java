package com.amhzing.participant.web.response;

import com.amhzing.participant.api.model.ParticipantInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@JsonInclude
public class QueryParticipantResponse {

    @NotNull
    @JsonProperty("participants")
    private final List<ParticipantInfo> participants;

    @NotNull
    @JsonProperty("errors")
    private final List<ResponseError> errors;

    private QueryParticipantResponse(final List<ParticipantInfo> participants, final List<ResponseError> errors) {
        this.participants = participants;
        this.errors = errors;
    }

    public static QueryParticipantResponse create(final List<ParticipantInfo> participants, final List<ResponseError> errors) {
        return new QueryParticipantResponse(participants, errors);
    }

    public List<ParticipantInfo> getParticipants() {
        return ImmutableList.copyOf(participants);
    }

    public List<ResponseError> getErrors() {
        return ImmutableList.copyOf(errors);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final QueryParticipantResponse that = (QueryParticipantResponse) o;
        return Objects.equals(participants, that.participants) &&
                Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participants, errors);
    }

    @Override
    public String toString() {
        return "QueryParticipantResponse{" +
                "participants=" + participants +
                ", errors=" + errors +
                '}';
    }
}
