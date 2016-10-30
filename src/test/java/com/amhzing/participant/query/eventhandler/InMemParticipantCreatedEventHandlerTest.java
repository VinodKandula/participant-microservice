package com.amhzing.participant.query.eventhandler;

import com.amhzing.participant.api.event.ParticipantCreatedEvent;
import com.amhzing.participant.query.data.jpa.mapping.ParticipantDetails;
import com.amhzing.participant.query.data.jpa.repository.ParticipantRepository;
import com.amhzing.participant.query.exception.QueryInsertException;
import org.axonframework.domain.MetaData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static com.amhzing.participant.helper.ParticipantApiModelHelper.*;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class InMemParticipantCreatedEventHandlerTest {

    @Mock
    private ParticipantRepository repository;

    @Mock
    private ParticipantCreatedEvent event;

    @Mock
    private MetaData metadata;

    private InMemParticipantCreatedEventHandler eventHandler;

    @Before
    public void setUp() {
        eventHandler = new InMemParticipantCreatedEventHandler(repository);
    }

    @Test(expected = QueryInsertException.class)
    public void should_fail_handling_event() throws Exception {
        given(repository.save(any(ParticipantDetails.class))).willThrow(RuntimeException.class);

        eventHandler.handleEvent(participantCreatedEvent(), metadata);

        fail("Should have failed before getting here.");
    }

    private ParticipantCreatedEvent participantCreatedEvent() {
        return ParticipantCreatedEvent.create(UUID.randomUUID(), name(), address(), contactNumber(), email());
    }
}