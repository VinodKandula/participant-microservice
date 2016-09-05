package com.amhzing.participant.query.data.jpa.mapping;


import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
public class ParticipantDetails {

    @Id
    private String participantId;

    @Embedded
    private Name name;

    @Embedded
    private Address address;

    private String email;

    private String contactNumber;

    private Date addedDate;

    private String addedBy;

    private Date updatedDate;

    private String updatedBy;

    public ParticipantDetails() {
    }

    private ParticipantDetails(final String participantId, final Name name, final Address address, final String email,
                               final String contactNumber, final Date addedDate, final String addedBy,
                               final Date updatedDate, final String updatedBy) {
        this.participantId = participantId;
        this.name = name;
        this.address = address;
        this.email = email;
        this.contactNumber = contactNumber;
        this.addedDate = addedDate;
        this.addedBy = addedBy;
        this.updatedDate = updatedDate;
        this.updatedBy = updatedBy;
    }

    public static ParticipantDetails create(final String participantId, final Name name, final Address address, final String email,
                                            final String contactNumber, final Date addedDate, final String addedBy,
                                            final Date updatedDate, final String updatedBy) {
        return new ParticipantDetails(participantId, name, address, email, contactNumber, addedDate, addedBy, updatedDate, updatedBy);
    }

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(final String participantId) {
        this.participantId = participantId;
    }

    public Name getName() {
        return name;
    }

    public void setName(final Name name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(final Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(final String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(final Date addedDate) {
        this.addedDate = addedDate;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(final String addedBy) {
        this.addedBy = addedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ParticipantDetails that = (ParticipantDetails) o;
        return Objects.equals(participantId, that.participantId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address) &&
                Objects.equals(email, that.email) &&
                Objects.equals(contactNumber, that.contactNumber) &&
                Objects.equals(addedDate, that.addedDate) &&
                Objects.equals(addedBy, that.addedBy) &&
                Objects.equals(updatedDate, that.updatedDate) &&
                Objects.equals(updatedBy, that.updatedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participantId, name, address, email, contactNumber, addedDate, addedBy, updatedDate, updatedBy);
    }

    @Override
    public String toString() {
        return "ParticipantDetails{" +
                "participantId=" + participantId +
                ", name=" + name +
                ", address=" + address +
                ", email='" + email + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", addedDate=" + addedDate +
                ", addedBy='" + addedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}
