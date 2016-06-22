package com.amhzing.participant.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class NameTest {

    @Test
    public void pass_if_last_name_is_not_blank() {
        final Name name = new Name(null, null, new LastName("Doe"), null);

        assertEquals(name.getFirstName(), null);
        assertEquals(name.getMiddleName(), null);
        assertEquals(name.getLastName().getValue(), "Doe");
        assertEquals(name.getSuffix(), null);
    }

    @Test(expected = NullPointerException.class)
    public void fail_if_last_name_is_null() {
        final Name name = new Name(null, null, null, null);

        fail(); // Fail if we got this far
    }

    @Test(expected = IllegalArgumentException.class)
    public void fail_if_last_name_is_blank() {
        final Name name = new Name(null, null, new LastName(""), null);

        fail(); // Fail if we got this far
    }
}