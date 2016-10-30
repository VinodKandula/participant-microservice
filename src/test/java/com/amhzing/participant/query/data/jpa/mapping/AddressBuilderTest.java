package com.amhzing.participant.query.data.jpa.mapping;

import org.junit.Test;

import static com.amhzing.participant.helper.ParticipantDetailsHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AddressBuilderTest {

    @Test
    public void should_build_address() {
        assertThat(builder()).isEqualTo(address());
    }

    private Address builder() {
        return new AddressBuilder().setAddressLine1(addressLine1())
                                   .setAddressLine2(addressLine2())
                                   .setCity(city())
                                   .setPostalCode(postalCode())
                                   .setCountry(country())
                                   .createAddress();
    }

    private Address address() {
        return Address.create(addressLine1(), addressLine2(), city(), postalCode(), country());
    }
}