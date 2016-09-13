package com.amhzing.participant.query.data;

public class QueryResponseBuilder {
    private String participantId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String suffix;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String country;
    private String postalCode;
    private String email;
    private String contactNumber;

    public QueryResponseBuilder setParticipantId(final String participantId) {
        this.participantId = participantId;
        return this;
    }

    public QueryResponseBuilder setFirstName(final String firstName) {
        this.firstName = firstName;
        return this;
    }

    public QueryResponseBuilder setMiddleName(final String middleName) {
        this.middleName = middleName;
        return this;
    }

    public QueryResponseBuilder setLastName(final String lastName) {
        this.lastName = lastName;
        return this;
    }

    public QueryResponseBuilder setSuffix(final String suffix) {
        this.suffix = suffix;
        return this;
    }

    public QueryResponseBuilder setAddressLine1(final String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public QueryResponseBuilder setAddressLine2(final String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public QueryResponseBuilder setCity(final String city) {
        this.city = city;
        return this;
    }

    public QueryResponseBuilder setCountry(final String country) {
        this.country = country;
        return this;
    }

    public QueryResponseBuilder setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public QueryResponseBuilder setEmail(final String email) {
        this.email = email;
        return this;
    }

    public QueryResponseBuilder setContactNumber(final String contactNumber) {
        this.contactNumber = contactNumber;
        return this;
    }

    public QueryResponse create() {
        return QueryResponse.create(participantId, firstName, middleName, lastName, suffix, addressLine1, addressLine2, city, country, postalCode, email, contactNumber);
    }
}