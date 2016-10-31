package com.amhzing.participant.command.domain;

import com.amhzing.participant.api.command.CreateParticipantCommand;
import com.amhzing.participant.api.event.ParticipantCreatedEvent;
import org.axonframework.domain.MetaData;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static com.amhzing.participant.helper.ParticipantApiModelHelper.*;
import static org.junit.Assert.fail;

public class ParticipantTest {

    private Participant participant;
    private UUID uuid = UUID.randomUUID();
    private MetaData metaData = MetaData.emptyInstance();

    @Before
    public void setUp() {
        participant = new Participant(command(), metaData);
    }

    @Test
    public void should_handle_event() {
        try {
            participant.handleEvent(event(), metaData);
        } catch(Exception ex) {
            fail("Should not have thrown any exception");
        }
    }

    private CreateParticipantCommand command() {
        return CreateParticipantCommand.create(uuid, name(), address(), contactNumber(), email());
    }

    private ParticipantCreatedEvent event() {
        return ParticipantCreatedEvent.create(uuid, name(), address(), contactNumber(), email());
    }
}