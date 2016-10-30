package com.amhzing.participant.query.data.jpa.mapping;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static com.amhzing.participant.helper.ParticipantDetailsHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ParticipantDetailsBuilderTest {

    private Date currentTime;
    private String participantId;

    @Before
    public void setUp() {
        currentTime = Calendar.getInstance().getTime();
        participantId = "12345";
    }

    @Test
    public void should_build_participant_details() {
        assertThat(builder()).isEqualTo(participantDetails());
    }

    private ParticipantDetails builder() {
        return new ParticipantDetailsBuilder().setParticipantId(participantId)
                                              .setName(name())
                                              .setAddress(address())
                                              .setEmail(email())
                                              .setContactNumber(contactNumber())
                                              .setAddedDate(currentTime)
                                              .setAddedBy(userId())
                                              .setUpdatedDate(currentTime)
                                              .setUpdatedBy(userId())
                                              .create();
    }

    private ParticipantDetails participantDetails() {
        return ParticipantDetails.create(participantId, name(), address(), email(), contactNumber(), currentTime, userId(), currentTime, userId());
    }

    private Name name() {
        return Name.create(firstName(), middleName(), lastName(), suffix());
    }

    private Address address() {
        return Address.create(addressLine1(), addressLine2(), city(), postalCode(), country());
    }
}