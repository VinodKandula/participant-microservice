package com.amhzing.participant.query.data;

import com.amhzing.participant.query.data.mapping.ParticipantDetails;

import java.util.List;

public interface QueryParticipant {

    List<ParticipantDetails> participantDetails(final QueryCriteria queryCriteria);
}
