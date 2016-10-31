package com.amhzing.participant.query.data;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.cassandra.core.CassandraTemplate;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.amhzing.participant.query.data.cassandra.mapping.ParticipantDetails.PARTICIPANT_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultQueryParticipantTest {

    @Mock
    private Session session;

    @Mock
    private CassandraTemplate cassandraTemplate;

    @Mock
    private ResultSet resultSet;

    @Mock
    private Row row;

    @Mock
    private ResultSetFuture resultSetFuture;

    private UUID participantId;
    private DefaultQueryParticipant defaultQueryParticipant;

    @Before
    public void setUp() {
        defaultQueryParticipant = new DefaultQueryParticipant(cassandraTemplate);
        participantId = UUID.randomUUID();
    }

    @Test
    public void should_find_participants_by_criteria() throws Exception {
        given(cassandraTemplate.getSession()).willReturn(session);
        given(session.execute(any(), any())).willReturn(resultSet);

        when(row.getUUID(PARTICIPANT_ID)).thenReturn(participantId);
        when(resultSet.all()).thenReturn(ImmutableList.of(row));

        final List<QueryResponse> byCriteria = defaultQueryParticipant.findByCriteria(queryCriteria());

        assertThat(byCriteria).hasSize(1);
        assertThat(byCriteria.get(0).getParticipantId()).isEqualTo(participantId.toString());
    }

    @Test
    public void should_find_participants_by_ids() throws Exception {
        given(cassandraTemplate.getSession()).willReturn(session);
        given(session.executeAsync(any(), any())).willReturn(resultSetFuture);

        when(resultSetFuture.get()).thenReturn(resultSet);
        when(row.getUUID(PARTICIPANT_ID)).thenReturn(participantId);
        when(resultSet.all()).thenReturn(ImmutableList.of(row));

        final List<QueryResponse> byIds = defaultQueryParticipant.findByIds(participantIds());

        assertThat(byIds).hasSize(1);
        assertThat(byIds.get(0).getParticipantId()).isEqualTo(participantId.toString());
    }

    private QueryCriteria queryCriteria() {
        return QueryCriteria.create("SE", "Stockholm", "Eln street 1", "Doe", UUID.randomUUID().toString());
    }

    private Set<ParticipantId> participantIds() {
        return ImmutableSet.of(ParticipantId.create(participantId));
    }
}