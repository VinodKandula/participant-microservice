package com.amhzing.participant.query.data;

import org.junit.Before;
import org.junit.Test;

import static com.amhzing.participant.helper.ParticipantDetailsHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

public class QueryResponseBuilderTest {

    private String participantId;

    @Before
    public void setUp() {
        participantId = "12345";
    }

    @Test
    public void should_build_response() {
        assertThat(builder()).isEqualTo(response());
    }

    private QueryResponse builder() {
        return new QueryResponseBuilder().setParticipantId(participantId)
                                         .setFirstName(firstName())
                                         .setLastName(lastName())
                                         .setMiddleName(middleName())
                                         .setSuffix(suffix())
                                         .setAddressLine1(addressLine1())
                                         .setAddressLine2(addressLine2())
                                         .setCity(city())
                                         .setCountry(country())
                                         .setPostalCode(postalCode())
                                         .setContactNumber(contactNumber())
                                         .setEmail(email())
                                         .create();
    }

    private QueryResponse response() {
        return QueryResponse.create(participantId, firstName(), middleName(), lastName(), suffix(),
                                    addressLine1(), addressLine2(), city(), country(), postalCode(),
                                    email(), contactNumber());
    }
}