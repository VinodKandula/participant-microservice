package com.amhzing.participant.query.data.cassandra.mapping;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static com.amhzing.participant.helper.ParticipantDetailsHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ParticipantPrimaryKeyBuilderTest {

    private UUID partcipantId;

    @Before
    public void setUp() {
        partcipantId = UUID.randomUUID();
    }

    @Test
    public void should_build_participant_key() {
        assertThat(builder()).isEqualTo(key());
    }

    private ParticipantPrimaryKey builder() {
        return new ParticipantPrimaryKeyBuilder().setCountry(country())
                                                 .setCity(city())
                                                 .setAddressLine1(addressLine1())
                                                 .setLastName(lastName())
                                                 .setParticipantId(partcipantId)
                                                 .create();
    }

    private ParticipantPrimaryKey key() {
        return ParticipantPrimaryKey.create(country(), city(), addressLine1(), lastName(), partcipantId);
    }
}