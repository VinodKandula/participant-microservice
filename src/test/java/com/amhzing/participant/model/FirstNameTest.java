package com.amhzing.participant.model;

import org.junit.Test;

import static com.amhzing.participant.model.FirstName.MAX_LENGTH;
import static org.apache.commons.lang.StringUtils.repeat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by mahanhz on 22/06/2016.
 */
public class FirstNameTest {

    @Test
    public void pass_if_value_is_not_blank() {
        final FirstName firstName = new FirstName("John");

        assertEquals(firstName.getValue(), "John");
    }

    @Test(expected = IllegalArgumentException.class)
    public void fail_if_value_is_blank() {
        final FirstName firstName = new FirstName("");

        fail(); // Fail if we got this far
    }

    @Test(expected = IllegalArgumentException.class)
    public void fail_if_value_is_greater_than_max_length() {
        final FirstName firstName = new FirstName("J" + repeat("x", MAX_LENGTH));

        fail(); // Fail if we got this far
    }
}