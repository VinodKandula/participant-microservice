package com.amhzing.participant.model;

import org.junit.Test;

import static com.amhzing.participant.model.FirstName.MAX_LENGTH;
import static org.apache.commons.lang.StringUtils.repeat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class LastNameTest {

    @Test
    public void pass_if_value_is_not_blank() {
        final LastName lastName = new LastName("John");

        assertEquals(lastName.getValue(), "John");
    }

    @Test(expected = IllegalArgumentException.class)
    public void fail_if_value_is_blank() {
        final LastName lastName = new LastName("");

        fail(); // Fail if we got this far
    }

    @Test(expected = IllegalArgumentException.class)
    public void fail_if_value_is_greater_than_max_length() {
        final LastName lastName = new LastName("J" + repeat("x", MAX_LENGTH));

        fail(); // Fail if we got this far
    }
}