package com.amhzing.participant.functionaltest;

import com.amhzing.participant.api.command.CreateParticipantCommand;
import com.amhzing.participant.api.event.ParticipantCreatedEvent;
import com.amhzing.participant.api.model.*;
import com.amhzing.participant.command.domain.Participant;
import com.fasterxml.uuid.Generators;
import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class ParticipantTest {

    private FixtureConfiguration fixture;

    private final UUID uuid = Generators.timeBasedGenerator().generate();

    @Before
    public void setUp() throws Exception {
        fixture = Fixtures.newGivenWhenThenFixture(Participant.class);
    }

    @Test
    public void should_generate_participant_created_event() throws Exception {
        fixture.given()
               .when(CreateParticipantCommand.create(uuid, name(), address(), contactNumber(), email()))
               .expectEvents(ParticipantCreatedEvent.create(uuid, name(), address(), contactNumber(), email()));
    }

    private Name name() {
        return Name.create("John", "Michael","Doe","II");
    }

    private Address address() {
        return Address.create("Elm Street 1", "c/o Freddy Cougar", "Nashville", "10291", Country.create("US", "United States"));
    }

    private ContactNumber contactNumber() {
        return ContactNumber.create("0749228216");
    }

    private Email email() {
        return Email.create("test-command@example.com");
    }
}
