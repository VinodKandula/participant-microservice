package com.amhzing.participant.command.domain;

import com.amhzing.participant.command.domain.LastName;
import com.amhzing.participant.command.domain.Name;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class NameTest {

    @Test
    public void pass_if_last_name_is_not_blank() {
        final Name name = Name.create(null, null, LastName.create("Doe"), null);

        assertEquals(name.getFirstName(), null);
        assertEquals(name.getMiddleName(), null);
        assertEquals(name.getLastName().getValue(), "Doe");
        assertEquals(name.getSuffix(), null);
    }

    @Test(expected = NullPointerException.class)
    public void fail_if_last_name_is_null() {
        Name.create(null, null, null, null);

        fail(); // Fail if we got this far
    }

    @Test(expected = IllegalArgumentException.class)
    public void fail_if_last_name_is_blank() {
        Name.create(null, null, LastName.create(""), null);

        fail(); // Fail if we got this far
    }
}