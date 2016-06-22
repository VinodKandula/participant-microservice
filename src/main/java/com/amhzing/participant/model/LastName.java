package com.amhzing.participant.model;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;

public class LastName {

    static final int MAX_LENGTH = 25;

    private final String value;

    public LastName(final String value) {
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
        LastName lastName = (LastName) o;
        return Objects.equals(value, lastName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "LastName{" +
                "value='" + value + '\'' +
                '}';
    }
}
