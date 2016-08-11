package com.amhzing.participant.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@JsonInclude
public class Address {
    private static final String LENGTH_HAS_BEEN_EXCEEDED = "Length has been exceeded";

    @NotNull @Size(message = LENGTH_HAS_BEEN_EXCEEDED, min= 1, max = 25)
    private final String addressLine1;
    @Size(message = LENGTH_HAS_BEEN_EXCEEDED, max = 25)
    private final String addressLine2;
    @NotNull @Size(message = LENGTH_HAS_BEEN_EXCEEDED, min= 1, max = 25)
    private final String city;
    @Size(message = LENGTH_HAS_BEEN_EXCEEDED, max = 10)
    private final String postalCode;
    @NotNull @Size(message = LENGTH_HAS_BEEN_EXCEEDED, min= 2, max = 3)
    private final String countryCode;
    @NotNull @Size(message = LENGTH_HAS_BEEN_EXCEEDED, min= 1, max = 100)
    private final String countryName;

    private Address(final String addressLine1,
                    final String addressLine2,
                    final String city,
                    final String postalCode,
                    final String countryCode,
                    final String countryName) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.postalCode = postalCode;
        this.countryCode = countryCode;
        this.countryName = countryName;
    }

    @JsonCreator
    public static Address create(@JsonProperty("addressLine1") final String addressLine1,
                                 @JsonProperty("addressLine2") final String addressLine2,
                                 @JsonProperty("city") final String city,
                                 @JsonProperty("postalCode") final String postalCode,
                                 @JsonProperty("countryCode") final String countryCode,
                                 @JsonProperty("countryName") final String countryName) {
        return new Address(addressLine1, addressLine2, city, postalCode, countryCode, countryName);
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

    public String getCountryCode() {
        return countryCode;
    }

    public String getCountryName() {
        return countryName;
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
                Objects.equals(countryCode, address.countryCode) &&
                Objects.equals(countryName, address.countryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressLine1, addressLine2, city, postalCode, countryCode, countryName);
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", countryName='" + countryName + '\'' +
                '}';
    }
}
