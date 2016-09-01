package com.amhzing.participant.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@JsonInclude
public class Address implements Serializable {
    private static final String INVALID_LENGTH = "Invalid length";

    @NotNull
    @Size(message = INVALID_LENGTH, min= 1, max = 25)
    private final String addressLine1;

    @Size(message = INVALID_LENGTH, max = 25)
    private final String addressLine2;

    @NotNull
    @Size(message = INVALID_LENGTH, min= 1, max = 25)
    private final String city;

    @Size(message = INVALID_LENGTH, max = 10)
    private final String postalCode;

    @NotNull @Valid
    private final Country country;

    private Address(final String addressLine1,
                    final String addressLine2,
                    final String city,
                    final String postalCode,
                    final Country country) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    @JsonCreator
    public static Address create(@JsonProperty("addressLine1") final String addressLine1,
                                 @JsonProperty("addressLine2") final String addressLine2,
                                 @JsonProperty("city") final String city,
                                 @JsonProperty("postalCode") final String postalCode,
                                 @JsonProperty("country") final Country country) {
        return new Address(addressLine1, addressLine2, city, postalCode, country);
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Country getCountry() {
        return country;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Address address = (Address) o;
        return Objects.equals(addressLine1, address.addressLine1) &&
                Objects.equals(addressLine2, address.addressLine2) &&
                Objects.equals(city, address.city) &&
                Objects.equals(postalCode, address.postalCode) &&
                Objects.equals(country, address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressLine1, addressLine2, city, postalCode, country);
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", country=" + country +
                '}';
    }
}
