package com.amhzing.participant.query.eventhandler;

import com.amhzing.participant.api.event.ParticipantCreatedEvent;
import com.amhzing.participant.query.exception.QueryInsertException;
import com.datastax.driver.core.querybuilder.Batch;
import org.axonframework.domain.MetaData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.cassandra.core.cql.CqlIdentifier;
import org.springframework.data.cassandra.convert.CassandraConverter;
import org.springframework.data.cassandra.core.CassandraTemplate;

import java.util.UUID;

import static com.amhzing.participant.helper.ParticipantApiModelHelper.*;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class ParticipantCreatedEventHandlerTest {

    @Mock
    private CassandraTemplate cassandraTemplate;

    @Mock
    private CassandraConverter cassandraConverter;

    private MetaData metadata = MetaData.emptyInstance();
    private CqlIdentifier cqlIdentifier = new CqlIdentifier("cql");
    private ParticipantCreatedEventHandler eventHandler;

    @Before
    public void setUp() {
        eventHandler = new ParticipantCreatedEventHandler(cassandraTemplate);
    }

    @Test(expected = QueryInsertException.class)
    public void should_fail_handling_event() throws Exception {
        given(cassandraTemplate.getTableName(any())).willReturn(cqlIdentifier);
        given(cassandraTemplate.getConverter()).willReturn(cassandraConverter);

        doThrow(new RuntimeException()).when(cassandraTemplate).execute(any(Batch.class));

        eventHandler.handleEvent(participantCreatedEvent(), metadata);

        fail("Should have failed before getting here.");
    }

    private ParticipantCreatedEvent participantCreatedEvent() {
        return ParticipantCreatedEvent.create(UUID.randomUUID(), name(), address(), contactNumber(), email());
    }
}