package com.amhzing.participant.query.data.jpa.mapping;

public class AddressBuilder {
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String postalCode;
    private String country;

    public AddressBuilder setAddressLine1(final String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public AddressBuilder setAddressLine2(final String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public AddressBuilder setCity(final String city) {
        this.city = city;
        return this;
    }

    public AddressBuilder setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public AddressBuilder setCountry(final String country) {
        this.country = country;
        return this;
    }

    public Address createAddress() {
        return Address.create(addressLine1, addressLine2, city, postalCode, country);
    }
}