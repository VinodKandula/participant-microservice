package com.amhzing.participant.query.data.cassandra.mapping;

import org.springframework.data.cassandra.mapping.Column;

import java.util.Date;
import java.util.Objects;

public class ParticipantDetails {

    @Column("first_name")
    private String firstName;

    @Column("middle_name")
    private String middleName;

    @Column("last_name")
    private String lastName;

    @Column("suffix")
    private String suffix;

    @Column("address_line1")
    private String addressLine1;

    @Column("address_line2")
    private String addressLine2;

    @Column("city")
    private String city;

    @Column("country")
    private String country;

    @Column("postal_code")
    private String postalCode;

    @Column(value = "email")
    private String email;

    @Column(value = "contact_number")
    private String contactNumber;

    @Column("added_date")
    private Date addedDate;

    @Column("added_by")
    private String addedBy;

    @Column("updated_date")
    private Date updatedDate;

    @Column("updated_by")
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
