package com.amhzing.participant.query.data.cassandra.mapping;

import java.util.Date;

public class ParticipantDetailsBuilder {
    private ParticipantPrimaryKey primaryKey;
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
    private Date addedDate;
    private String addedBy;
    private Date updatedDate;
    private String updatedBy;

    public ParticipantDetailsBuilder setPrimaryKey(final ParticipantPrimaryKey primaryKey) {
        this.primaryKey = primaryKey;
        return this;
    }

    public ParticipantDetailsBuilder setFirstName(final String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ParticipantDetailsBuilder setMiddleName(final String middleName) {
        this.middleName = middleName;
        return this;
    }

    public ParticipantDetailsBuilder setLastName(final String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ParticipantDetailsBuilder setSuffix(final String suffix) {
        this.suffix = suffix;
        return this;
    }

    public ParticipantDetailsBuilder setAddressLine1(final String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public ParticipantDetailsBuilder setAddressLine2(final String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public ParticipantDetailsBuilder setCity(final String city) {
        this.city = city;
        return this;
    }

    public ParticipantDetailsBuilder setCountry(final String country) {
        this.country = country;
        return this;
    }

    public ParticipantDetailsBuilder setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public ParticipantDetailsBuilder setEmail(final String email) {
        this.email = email;
        return this;
    }

    public ParticipantDetailsBuilder setContactNumber(final String contactNumber) {
        this.contactNumber = contactNumber;
        return this;
    }

    public ParticipantDetailsBuilder setAddedDate(final Date addedDate) {
        this.addedDate = new Date(addedDate.getTime());
        return this;
    }

    public ParticipantDetailsBuilder setAddedBy(final String addedBy) {
        this.addedBy = addedBy;
        return this;
    }

    public ParticipantDetailsBuilder setUpdatedDate(final Date updatedDate) {
        this.updatedDate = new Date(updatedDate.getTime());
        return this;
    }

    public ParticipantDetailsBuilder setUpdatedBy(final String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public ParticipantDetails create() {
        return ParticipantDetails.create(primaryKey, firstName, middleName, lastName, suffix,
                                         addressLine1, addressLine2, city, country, postalCode,
                                         email, contactNumber, addedDate, addedBy, updatedDate, updatedBy);
    }
}