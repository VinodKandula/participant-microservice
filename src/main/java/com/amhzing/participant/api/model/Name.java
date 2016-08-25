package com.amhzing.participant.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@JsonInclude
public class Name {
    private static final String LENGTH_HAS_BEEN_EXCEEDED = "Length has been exceeded";

    @Size(message = LENGTH_HAS_BEEN_EXCEEDED, max = 25)
    private final String firstName;
    @Size(message = LENGTH_HAS_BEEN_EXCEEDED, max = 25)
    private final String middleName;
    @NotNull @Size(message = LENGTH_HAS_BEEN_EXCEEDED, min = 1, max = 25)
    private final String lastName;
    @Size(message = LENGTH_HAS_BEEN_EXCEEDED, max = 10)
    private final String suffix;


    private Name(final String firstName, final String middleName, final String lastName, final String suffix) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.suffix = suffix;
    }

    @JsonCreator
    public static Name create(@JsonProperty("firstName") final String firstName,
                              @JsonProperty("middleName") final String middleName,
                              @JsonProperty("lastName") final String lastName,
                              @JsonProperty("suffix") final String suffix) {
        return new Name(firstName, middleName, lastName, suffix);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSuffix() {
        return suffix;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Name name = (Name) o;
        return Objects.equals(firstName, name.firstName) &&
                Objects.equals(middleName, name.middleName) &&
                Objects.equals(lastName, name.lastName) &&
                Objects.equals(suffix, name.suffix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, middleName, lastName, suffix);
    }

    @Override
    public String toString() {
        return "Name{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", suffix='" + suffix + '\'' +
                '}';
    }
}
