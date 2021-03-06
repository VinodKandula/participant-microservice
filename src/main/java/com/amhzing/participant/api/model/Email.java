package com.amhzing.participant.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@JsonInclude
public class Email implements Serializable {

    private static final String INVALID_LENGTH = "Invalid length";

    @Size(message = INVALID_LENGTH, max = 50)
    @JsonProperty("primaryEmail")
    private final String value;

    private Email(final String value) {
        this.value = value;
    }

    @JsonCreator
    public static Email create(@JsonProperty("primaryEmail") final String email) {
        return new Email(email);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Email{" +
                "value='" + value + '\'' +
                '}';
    }
}
