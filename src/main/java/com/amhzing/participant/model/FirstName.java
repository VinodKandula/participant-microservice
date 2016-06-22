package com.amhzing.participant.model;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;

/**
 * Created by mahanhz on 22/06/2016.
 */
public class FirstName {

    private static final int MAX_LENGTH = 25;

    private final String value;

    public FirstName(final String value) {
        notBlank(value);
        isTrue(value.length() <= MAX_LENGTH);

        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FirstName firstName = (FirstName) o;
        return Objects.equals(value, firstName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "FirstName{" +
                "value='" + value + '\'' +
                '}';
    }
}
