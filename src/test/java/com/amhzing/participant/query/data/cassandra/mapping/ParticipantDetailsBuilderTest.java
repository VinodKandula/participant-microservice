package com.amhzing.participant.query.data.cassandra.mapping;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static com.amhzing.participant.helper.ParticipantDetailsHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ParticipantDetailsBuilderTest {

    private Date currentTime;

    @Before
    public void setUp() {
        currentTime = Calendar.getInstance().getTime();
    }

    @Test
    public void should_build_participant_details() {
        assertThat(builder()).isEqualTo(participantDetails());
    }

    private ParticipantDetails builder() {
        return participantDetailsBuilder(currentTime);
    }

    private ParticipantDetails participantDetails() {
        return new ParticipantDetails(firstName(), middleName(), lastName(), suffix(),
                                      addressLine1(), addressLine2(), city(), country(), postalCode(),
                                      email(), contactNumber(), currentTime, userId(), currentTime, userId());
    }
}