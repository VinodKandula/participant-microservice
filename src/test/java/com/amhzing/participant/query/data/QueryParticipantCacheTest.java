package com.amhzing.participant.query.data;

import com.fasterxml.uuid.Generators;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsSame.sameInstance;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CacheTestConfig.class)
public class QueryParticipantCacheTest {

    @Autowired
    private QueryParticipant queryParticipant;

    private UUID participantId = Generators.timeBasedGenerator().generate();

    @Test
    public void should_invoke_cache() throws Exception {

        // First call
        final List<QueryResponse> firstCallResponse = queryParticipant.findByIds(setOfParticipantIds());

        // Second call
        final List<QueryResponse> secondCallResponse = queryParticipant.findByIds(setOfParticipantIds());

        assertThat(firstCallResponse, sameInstance(secondCallResponse));
    }

    private ImmutableSet<ParticipantId> setOfParticipantIds() {
        return ImmutableSet.of(ParticipantId.create(participantId));
    }
}