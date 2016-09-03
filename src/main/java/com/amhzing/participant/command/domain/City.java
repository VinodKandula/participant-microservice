package com.amhzing.participant.command.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.trim;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;

public class City implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(City.class);

    static final int MAX_LENGTH = 25;

    private String value;

    private City(final String value) {
        isValid(value);

        this.value = trim(value);
    }

    public static City create(final String value) {
        return new City(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final City city = (City) o;
        return Objects.equals(value, city.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "City{" +
                "value='" + value + '\'' +
                '}';
    }

    private boolean isValid(final String value) {
        notBlank(value);
        isTrue(value.length() <= MAX_LENGTH);

        return true;
    }
}
