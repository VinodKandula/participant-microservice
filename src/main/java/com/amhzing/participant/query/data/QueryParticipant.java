package com.amhzing.participant.query.data;

import java.util.List;
import java.util.Set;

public interface QueryParticipant {

    List<QueryResponse> findByCriteria(final QueryCriteria queryCriteria);

    List<QueryResponse> findByIds(final Set<ParticipantId> participantIds);
}
