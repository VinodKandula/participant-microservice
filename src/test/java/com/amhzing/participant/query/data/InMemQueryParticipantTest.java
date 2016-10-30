package com.amhzing.participant.query.data;

import com.amhzing.participant.query.data.jpa.repository.ParticipantQueryDslRepository;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.querydsl.core.types.Predicate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.amhzing.participant.helper.ParticipantRepositoryHelper.participant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class InMemQueryParticipantTest {

    @Mock
    private ParticipantQueryDslRepository repository;

    private UUID participantId;
    private InMemQueryParticipant queryParticipant;

    @Before
    public void setUp() {
        queryParticipant = new InMemQueryParticipant(repository);
        participantId = UUID.randomUUID();
    }

    @Test
    public void should_find_participants_by_criteria() throws Exception {
        given(repository.findAll(any(Predicate.class))).willReturn(ImmutableList.of(participant(participantId)));

        final List<QueryResponse> byCriteria = queryParticipant.findByCriteria(queryCriteria());

        assertThat(byCriteria).hasSize(1);
        assertThat(byCriteria.get(0).getParticipantId()).isEqualTo(participantId.toString());
    }

    @Test
    public void should_find_participants_by_ids() throws Exception {
        given(repository.findByParticipantIdIn(any())).willReturn(ImmutableList.of(participant(participantId)));

        final List<QueryResponse> byCriteria = queryParticipant.findByIds(participantIds());

        assertThat(byCriteria).hasSize(1);
        assertThat(byCriteria.get(0).getParticipantId()).isEqualTo(participantId.toString());
    }

    private QueryCriteria queryCriteria() {
        return QueryCriteria.create("SE", "Stockholm", "Eln street 1", "Doe", UUID.randomUUID().toString());
    }

    private Set<ParticipantId> participantIds() {
        return ImmutableSet.of(ParticipantId.create(participantId));
    }
}