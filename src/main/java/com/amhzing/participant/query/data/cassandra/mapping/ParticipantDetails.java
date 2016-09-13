package com.amhzing.participant.query.data.cassandra.mapping;

import org.springframework.data.cassandra.mapping.Column;

import java.util.Date;
import java.util.Objects;

public class ParticipantDetails {

    public static final String PARTICIPANT_ID = "participant_id";
    public static final String FIRST_NAME = "first_name";
    public static final String MIDDLE_NAME = "middle_name";
    public static final String LAST_NAME = "last_name";
    public static final String SUFFIX = "suffix";
    public static final String ADDRESS_LINE1 = "address_line1";
    public static final String ADDRESS_LINE2 = "address_line2";
    public static final String CITY = "city";
    public static final String COUNTRY = "country";
    public static final String POSTAL_CODE = "postal_code";
    public static final String EMAIL = "email";
    public static final String CONTACT_NUMBER = "contact_number";
    public static final String ADDED_DATE = "added_date";
    public static final String ADDED_BY = "added_by";
    public static final String UPDATED_DATE = "updated_date";
    public static final String UPDATED_BY = "updated_by";

    @Column(FIRST_NAME)
    private String firstName;

    @Column(MIDDLE_NAME)
    private String middleName;

    @Column(LAST_NAME)
    private String lastName;

    @Column(SUFFIX)
    private String suffix;

    @Column(ADDRESS_LINE1)
    private String addressLine1;

    @Column(ADDRESS_LINE2)
    private String addressLine2;

    @Column(CITY)
    private String city;

    @Column(COUNTRY)
    private String country;

    @Column(POSTAL_CODE)
    private String postalCode;

    @Column(EMAIL)
    private String email;

    @Column(CONTACT_NUMBER)
    private String contactNumber;

    @Column(ADDED_DATE)
    private Date addedDate;

    @Column(ADDED_BY)
    private String addedBy;

    @Column(UPDATED_DATE)
    private Date updatedDate;

    @Column(UPDATED_BY)
    private String updatedBy;

    public ParticipantDetails() {
    }

    public ParticipantDetails(final String firstName, final String middleName, final String lastName, final String suffix,
                              final String addressLine1, final String addressLine2, final String city, final String country,
                              final String postalCode, final String email, final String contactNumber, final Date addedDate,
                              final String addedBy, final Date updatedDate, final String updatedBy) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.suffix = suffix;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
        this.email = email;
        this.contactNumber = contactNumber;
        this.addedDate = addedDate;
        this.addedBy = addedBy;
        this.updatedDate = updatedDate;
        this.updatedBy = updatedBy;
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

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public Date getAddedDate() {
        return new Date(addedDate.getTime());
    }

    public String getAddedBy() {
        return addedBy;
    }

    public Date getUpdatedDate() {
        return new Date(updatedDate.getTime());
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ParticipantDetails that = (ParticipantDetails) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(middleName, that.middleName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(suffix, that.suffix) &&
                Objects.equals(addressLine1, that.addressLine1) &&
                Objects.equals(addressLine2, that.addressLine2) &&
                Objects.equals(city, that.city) &&
                Objects.equals(country, that.country) &&
                Objects.equals(postalCode, that.postalCode) &&
                Objects.equals(email, that.email) &&
                Objects.equals(contactNumber, that.contactNumber) &&
                Objects.equals(addedDate, that.addedDate) &&
                Objects.equals(addedBy, that.addedBy) &&
                Objects.equals(updatedDate, that.updatedDate) &&
                Objects.equals(updatedBy, that.updatedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, middleName, lastName, suffix, addressLine1, addressLine2, city, country,
                            postalCode, email, contactNumber, addedDate, addedBy, updatedDate, updatedBy);
    }

    @Override
    public String toString() {
        return "ParticipantDetails{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", suffix='" + suffix + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", email='" + email + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", addedDate=" + addedDate +
                ", addedBy='" + addedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}
