package com.amhzing.participant.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Size;
import java.util.Objects;

public class ContactNumber {
    private static final String LENGTH_HAS_BEEN_EXCEEDED = "Length has been exceeded";

    @Size(message = LENGTH_HAS_BEEN_EXCEEDED, max = 25)
    private final String value;

    private ContactNumber(final String value) {
        this.value = value;
    }

    @JsonCreator
    public static ContactNumber create(@JsonProperty("contactNumber") final String contactNumber) {
        return new ContactNumber(contactNumber);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ContactNumber that = (ContactNumber) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "ContactNumber{" +
                "value='" + value + '\'' +
                '}';
    }
}
