package com.amhzing.participant.query.data;

import java.util.List;

public interface QueryParticipant {

    List<QueryResponse> participantDetails(final QueryCriteria queryCriteria);
}
