package com.amhzing.participant.command.domain;

import com.amhzing.participant.command.domain.LastName;
import org.junit.Test;

import static com.amhzing.participant.command.domain.FirstName.MAX_LENGTH;
import static org.apache.commons.lang.StringUtils.repeat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class LastNameTest {

    @Test
    public void pass_if_value_is_not_blank() {
        final LastName lastName = LastName.create("John");

        assertEquals(lastName.getValue(), "John");
    }

    @Test(expected = IllegalArgumentException.class)
    public void fail_if_value_is_blank() {
        LastName.create("");

        fail(); // Fail if we got this far
    }

    @Test(expected = IllegalArgumentException.class)
    public void fail_if_value_is_greater_than_max_length() {
        LastName.create("J" + repeat("x", MAX_LENGTH));

        fail(); // Fail if we got this far
    }
}