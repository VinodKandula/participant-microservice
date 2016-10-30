package com.amhzing.participant.command.domain;

import org.junit.Test;

import static com.amhzing.participant.command.domain.City.MAX_LENGTH;
import static org.apache.commons.lang.StringUtils.repeat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CityTest {

    @Test
    public void pass_if_value_is_not_blank() {
        final City city = City.create("Stockholm");

        assertEquals(city.getValue(), "Stockholm");
    }

    @Test(expected = IllegalArgumentException.class)
    public void fail_if_value_is_blank() {
        City.create("");

        fail(); // Fail if we got this far
    }

    @Test(expected = IllegalArgumentException.class)
    public void fail_if_value_is_greater_than_max_length() {
        City.create("J" + repeat("x", MAX_LENGTH));

        fail(); // Fail if we got this far
    }
}