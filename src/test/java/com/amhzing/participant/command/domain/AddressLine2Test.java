package com.amhzing.participant.command.domain;

import org.junit.Test;

import static com.amhzing.participant.command.domain.AddressLine2.MAX_LENGTH;
import static org.apache.commons.lang.StringUtils.repeat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AddressLine2Test {

    @Test
    public void pass_if_value_is_not_blank() {
        final AddressLine2 addressLine2 = AddressLine2.create("Street");

        assertEquals(addressLine2.getValue(), "Street");
    }

    @Test(expected = IllegalArgumentException.class)
    public void fail_if_value_is_blank() {
        AddressLine2.create("");

        fail(); // Fail if we got this far
    }

    @Test(expected = IllegalArgumentException.class)
    public void fail_if_value_is_greater_than_max_length() {
        AddressLine2.create("J" + repeat("x", MAX_LENGTH));

        fail(); // Fail if we got this far
    }
}