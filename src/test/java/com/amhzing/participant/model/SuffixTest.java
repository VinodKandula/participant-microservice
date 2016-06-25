package com.amhzing.participant.model;

import org.junit.Test;

import static com.amhzing.participant.model.FirstName.MAX_LENGTH;
import static org.apache.commons.lang.StringUtils.repeat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SuffixTest {

    @Test
    public void pass_if_value_is_not_blank() {
        final Suffix suffix = Suffix.create("John");

        assertEquals(suffix.getValue(), "John");
    }

    @Test(expected = IllegalArgumentException.class)
    public void fail_if_value_is_blank() {
        final Suffix suffix = Suffix.create("");

        fail(); // Fail if we got this far
    }

    @Test(expected = IllegalArgumentException.class)
    public void fail_if_value_is_greater_than_max_length() {
        final Suffix suffix = Suffix.create("J" + repeat("x", MAX_LENGTH));

        fail(); // Fail if we got this far
    }
}