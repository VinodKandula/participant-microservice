package com.amhzing.participant.api.event;

import org.junit.Test;

import java.util.UUID;

import static com.amhzing.participant.helper.ParticipantApiModelHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class ParticipantCreatedEventTest {

    private UUID uuid = UUID.randomUUID();

    @Test
    public void should_create_valid_event() {
        final ParticipantCreatedEvent event = ParticipantCreatedEvent.create(uuid, name(), address(), contactNumber(), email());

        assertThat(event.getName().getLastName()).isEqualTo("lName");
    }

    @Test(expected = NullPointerException.class)
    public void should_fail_to_create_if_missing_id() {
        ParticipantCreatedEvent.create(null, name(), address(), contactNumber(), email());

        fail("Should not have gotten this far");
    }

    @Test(expected = NullPointerException.class)
    public void should_fail_to_create_if_missing_name() {
        ParticipantCreatedEvent.create(uuid, null, address(), contactNumber(), email());

        fail("Should not have gotten this far");
    }

    @Test(expected = NullPointerException.class)
    public void should_fail_to_create_if_missing_address() {
        ParticipantCreatedEvent.create(uuid, name(), null, contactNumber(), email());

        fail("Should not have gotten this far");
    }
}