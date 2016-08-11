package com.amhzing.participant.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Country {
    private static final String LENGTH_HAS_BEEN_EXCEEDED = "Length has been exceeded";

    @NotNull @Size(message = LENGTH_HAS_BEEN_EXCEEDED, min= 2, max = 3)
    private final String code;
    @NotNull @Size(message = LENGTH_HAS_BEEN_EXCEEDED, min= 1, max = 100)
    private final String name;

    private Country(final String code, final String name) {
        this.code = code;
        this.name = name;
    }

    @JsonCreator
    public static Country create(@JsonProperty("code") final String code,
                                 @JsonProperty("name") final String name) {
        return new Country(code, name);
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Country country = (Country) o;
        return Objects.equals(code, country.code) &&
                Objects.equals(name, country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name);
    }

    @Override
    public String toString() {
        return "Country{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
