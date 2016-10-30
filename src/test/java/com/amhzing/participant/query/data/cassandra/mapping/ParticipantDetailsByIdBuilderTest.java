package com.amhzing.participant.query.data.cassandra.mapping;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static com.amhzing.participant.helper.ParticipantDetailsHelper.*;
import static com.amhzing.participant.helper.ParticipantDetailsHelper.userId;
import static org.assertj.core.api.Assertions.assertThat;

public class ParticipantDetailsByIdBuilderTest {

    private Date currentTime;
    private UUID partcipantId;

    @Before
    public void setUp() {
        currentTime = Calendar.getInstance().getTime();
        partcipantId = UUID.randomUUID();
    }

    @Test
    public void should_build_participants_details() {
        assertThat(builder()).isEqualTo(participantDetails());
    }

    private ParticipantDetailsById builder() {
        return new ParticipantDetailsByIdBuilder().setParticipantId(partcipantId)
                                                  .setParticipantDetails(participantDetails())
                                                  .create();
    }

    private ParticipantDetailsById participantDetails() {
        return ParticipantDetailsById.create(firstName(), middleName(), lastName(), suffix(),
                                             addressLine1(), addressLine2(), city(), country(), postalCode(),
                                             email(), contactNumber(), currentTime, userId(), currentTime,
                                             userId(), partcipantId);
    }
}