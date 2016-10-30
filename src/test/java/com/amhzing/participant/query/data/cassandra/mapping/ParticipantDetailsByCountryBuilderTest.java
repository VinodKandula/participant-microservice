package com.amhzing.participant.query.data.cassandra.mapping;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static com.amhzing.participant.helper.ParticipantDetailsHelper.*;
import static com.amhzing.participant.helper.ParticipantDetailsHelper.contactNumber;
import static com.amhzing.participant.helper.ParticipantDetailsHelper.userId;
import static org.assertj.core.api.Assertions.assertThat;

public class ParticipantDetailsByCountryBuilderTest {

    private Date currentTime;
    private UUID participantId;

    @Before
    public void setUp() {
        currentTime = Calendar.getInstance().getTime();
        participantId = UUID.randomUUID();
    }

    @Test
    public void should_build_participant_details() {
        assertThat(builder()).isEqualTo(participantDetails());
    }

    private ParticipantDetailsByCountry builder() {
        return new ParticipantDetailsByCountryBuilder().setParticipantDetails(participantDetailsBuilder(currentTime))
                                                       .setPrimaryKey(key())
                                                       .create();
    }

    private ParticipantDetailsByCountry participantDetails() {
        return ParticipantDetailsByCountry.create(firstName(), middleName(), lastName(), suffix(),
                                                  addressLine1(), addressLine2(), city(), country(), postalCode(),
                                                  email(), contactNumber(), currentTime, userId(), currentTime,
                                                  userId(), key());
    }

    private ParticipantPrimaryKey key() {
        return ParticipantPrimaryKey.create("SE", "Stockholm", "Street1", "Doe", participantId);
    }
}