package com.amhzing.participant.command.domain;

import org.junit.Test;

import static com.amhzing.participant.command.domain.AddressLine1.MAX_LENGTH;
import static org.apache.commons.lang.StringUtils.repeat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AddressLine1Test {

    @Test
    public void pass_if_value_is_not_blank() {
        final AddressLine1 addressLine1 = AddressLine1.create("Street");

        assertEquals(addressLine1.getValue(), "Street");
    }

    @Test(expected = IllegalArgumentException.class)
    public void fail_if_value_is_blank() {
        AddressLine1.create("");

        fail(); // Fail if we got this far
    }

    @Test(expected = IllegalArgumentException.class)
    public void fail_if_value_is_greater_than_max_length() {
        AddressLine1.create("J" + repeat("x", MAX_LENGTH));

        fail(); // Fail if we got this far
    }
}