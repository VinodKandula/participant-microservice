package com.amhzing.participant.command.domain;

import com.amhzing.participant.api.event.ParticipantCreatedEvent;
import org.junit.Test;

import java.util.UUID;

import static com.amhzing.participant.helper.ParticipantApiModelHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ParticipantFactoryTest {

    private UUID uuid = UUID.randomUUID();

    @Test
    public void should_create_name() {
        final Name name = ParticipantFactory.createNameFrom(event());

        assertThat(name.getFirstName().getValue()).isEqualTo(name().getFirstName());
        assertThat(name.getMiddleName().getValue()).isEqualTo(name().getMiddleName());
        assertThat(name.getLastName().getValue()).isEqualTo(name().getLastName());
        assertThat(name.getSuffix().getValue()).isEqualTo(name().getSuffix());
    }

    @Test
    public void should_create_address() {
        final Address address = ParticipantFactory.createAddressFrom(event());

        assertThat(address.getAddressLine1().getValue()).isEqualTo(address().getAddressLine1());
        assertThat(address.getAddressLine2().getValue()).isEqualTo(address().getAddressLine2());
        assertThat(address.getCity().getValue()).isEqualTo(address().getCity());
        assertThat(address.getCountry().getCode()).isEqualTo(address().getCountry().getCode());
        assertThat(address.getCountry().getName()).isEqualTo(address().getCountry().getName());
        assertThat(address.getPostalCode().getValue()).isEqualTo(address().getPostalCode());
    }

    @Test
    public void should_create_contact_number() {
        final ContactNumber contactNumber = ParticipantFactory.createContactNumberFrom(event());

        assertThat(contactNumber.getValue()).isEqualTo(contactNumber().getValue());
    }

    @Test
    public void should_create_email() {
        final Email email = ParticipantFactory.createEmailFrom(event());

        assertThat(email.getValue()).isEqualTo(email().getValue());
    }

    private ParticipantCreatedEvent event() {
        return ParticipantCreatedEvent.create(uuid, name(), address(), contactNumber(), email());
    }
}