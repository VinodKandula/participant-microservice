package com.amhzing.participant.query.data;

import java.util.List;

public interface QueryParticipant {

    List<QueryResponse> findByCriteria(final QueryCriteria queryCriteria);

    List<QueryResponse> findByIds(final List<ParticipantId> participantIds);
}
