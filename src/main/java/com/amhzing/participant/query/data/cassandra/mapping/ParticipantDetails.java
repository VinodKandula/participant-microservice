package com.amhzing.participant.query.data.cassandra.mapping;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.util.Date;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

@Table(value = "participant_details")
public class ParticipantDetails {

    @PrimaryKey
    private ParticipantPrimaryKey primaryKey;

    @Column(value = "first_name")
    private String firstName;

    @Column(value = "middle_name")
    private String middleName;

    @Column(value = "suffix")
    private String suffix;

    @Column(value = "address_line2")
    private String addressLine2;

    @Column(value = "postal_code")
    private String postalCode;

    @Column(value = "email")
    private String email;

    @Column(value = "contact_number")
    private String contactNumber;

    @Column(value = "added_date")
    private Date addedDate;

    @Column(value = "added_by")
    private String addedBy;

    @Column(value = "updated_date")
    private Date updatedDate;

    @Column(value = "updated_by")
    private String updatedBy;

    public ParticipantDetails() {
    }

    private ParticipantDetails(final ParticipantPrimaryKey primaryKey, final String firstName, final String middleName,
                               final String suffix, final String addressLine2, final String postalCode,
                               final String email, final String contactNumber, final Date addedDate,
                               final String addedBy, final Date updatedDate, final String updatedBy) {
        this.primaryKey = notNull(primaryKey);
        this.firstName = firstName;
        this.middleName = middleName;
        this.suffix = suffix;
        this.addressLine2 = addressLine2;
        this.postalCode = postalCode;
        this.email = email;
        this.contactNumber = contactNumber;
        this.addedDate = addedDate;
        this.addedBy = addedBy;
        this.updatedDate = updatedDate;
        this.updatedBy = updatedBy;
    }

    public static ParticipantDetails create(final ParticipantPrimaryKey primaryKey, final String firstName, final String middleName,
                                            final String suffix, final String addressLine2, final String postalCode,
                                            final String email, final String contactNumber, final Date addedDate,
                                            final String addedBy, final Date updatedDate, final String updatedBy) {
        return new ParticipantDetails(primaryKey, firstName, middleName, suffix, addressLine2, postalCode, email,
                                      contactNumber, addedDate, addedBy, updatedDate, updatedBy);
    }

    public ParticipantPrimaryKey getPrimaryKey() {
        return primaryKey;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getAddressLine2() {
        return addressLine2;
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
        return Objects.equals(primaryKey, that.primaryKey) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(middleName, that.middleName) &&
                Objects.equals(suffix, that.suffix) &&
                Objects.equals(addressLine2, that.addressLine2) &&
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
        return Objects.hash(primaryKey, firstName, middleName, suffix, addressLine2, postalCode, email, contactNumber, addedDate, addedBy, updatedDate, updatedBy);
    }

    @Override
    public String toString() {
        return "ParticipantInfo{" +
                "primaryKey=" + primaryKey +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", suffix='" + suffix + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
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
