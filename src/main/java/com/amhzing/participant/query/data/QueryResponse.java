package com.amhzing.participant.query.data;

import java.util.Objects;

public class QueryResponse {

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

    private QueryResponse(final String participantId, final String firstName, final String middleName,
                          final String lastName, final String suffix, final String addressLine1,
                          final String addressLine2, final String city, final String country,
                          final String postalCode, final String email, final String contactNumber) {
        this.participantId = participantId;
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
    }

    public static QueryResponse create(final String participantId, final String firstName, final String middleName,
                                       final String lastName, final String suffix, final String addressLine1,
                                       final String addressLine2, final String city, final String country,
                                       final String postalCode, final String email, final String contactNumber) {
        return new QueryResponse(participantId, firstName, middleName, lastName, suffix, addressLine1, addressLine2, city, country, postalCode, email, contactNumber);
    }

    public String getParticipantId() {
        return participantId;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final QueryResponse that = (QueryResponse) o;
        return Objects.equals(participantId, that.participantId) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(middleName, that.middleName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(suffix, that.suffix) &&
                Objects.equals(addressLine1, that.addressLine1) &&
                Objects.equals(addressLine2, that.addressLine2) &&
                Objects.equals(city, that.city) &&
                Objects.equals(country, that.country) &&
                Objects.equals(postalCode, that.postalCode) &&
                Objects.equals(email, that.email) &&
                Objects.equals(contactNumber, that.contactNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participantId, firstName, middleName, lastName, suffix, addressLine1, addressLine2, city, country, postalCode, email, contactNumber);
    }

    @Override
    public String toString() {
        return "QueryResponse{" +
                "participantId='" + participantId + '\'' +
                ", firstName='" + firstName + '\'' +
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
                '}';
    }
}
