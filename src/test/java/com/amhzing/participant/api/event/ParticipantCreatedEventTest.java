package com.amhzing.participant.api.event;

import com.amhzing.participant.api.model.Address;
import com.amhzing.participant.api.model.ContactNumber;
import com.amhzing.participant.api.model.Country;
import com.amhzing.participant.api.model.Name;
import org.junit.Test;

import java.util.UUID;

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

    private Address address() {
        return Address.create("ad1", "ad2", "city", "pCode", Country.create("SE", ""));
    }

    private Name name() {
        return Name.create("fname", "mName", "lName", "II");
    }

    private ContactNumber contactNumber() {
        return ContactNumber.create("12345678");
    }

    private com.amhzing.participant.api.model.Email email() {
        return com.amhzing.participant.api.model.Email.create("test@example.com");
    }
}