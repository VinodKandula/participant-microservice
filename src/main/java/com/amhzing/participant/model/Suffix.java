package com.amhzing.participant.model;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;

public class Suffix {

    static final int MAX_LENGTH = 10;

    private final String value;

    public Suffix(final String value) {
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
        Suffix suffix = (Suffix) o;
        return Objects.equals(value, suffix.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Suffix{" +
                "value='" + value + '\'' +
                '}';
    }
}
