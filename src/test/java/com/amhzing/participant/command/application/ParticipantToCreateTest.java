package com.amhzing.participant.command.application;

import com.amhzing.participant.helper.ParticipantApiModelHelper;
import org.junit.Test;

import java.util.UUID;

import static com.amhzing.participant.helper.ParticipantApiModelHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class ParticipantToCreateTest {

    private UUID uuid = UUID.randomUUID();

    @Test
    public void should_create_valid_command() {
        final ParticipantToCreate participant = ParticipantApiModelHelper.participantToCreate();

        assertThat(participant.getName().getLastName()).isEqualTo("lName");
    }

    @Test(expected = NullPointerException.class)
    public void should_fail_to_create_if_missing_name() {
        ParticipantToCreate.create(null, address(), contactNumber(), email(), user());

        fail("Should not have gotten this far");
    }

    @Test(expected = NullPointerException.class)
    public void should_fail_to_create_if_missing_address() {
        ParticipantToCreate.create(name(), null, contactNumber(), email(), user());

        fail("Should not have gotten this far");
    }

    @Test(expected = NullPointerException.class)
    public void should_fail_to_create_if_missing_user() {
        ParticipantToCreate.create(name(), address(), contactNumber(), email(), null);

        fail("Should not have gotten this far");
    }
}