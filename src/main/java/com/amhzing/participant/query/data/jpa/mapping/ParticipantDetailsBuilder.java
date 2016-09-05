package com.amhzing.participant.query.data.jpa.mapping;

import java.util.Date;

public class ParticipantDetailsBuilder {
    private String participantId;
    private Name name;
    private Address address;
    private String email;
    private String contactNumber;
    private Date addedDate;
    private String addedBy;
    private Date updatedDate;
    private String updatedBy;

    public ParticipantDetailsBuilder setParticipantId(final String participantId) {
        this.participantId = participantId;
        return this;
    }

    public ParticipantDetailsBuilder setName(final Name name) {
        this.name = name;
        return this;
    }

    public ParticipantDetailsBuilder setAddress(final Address address) {
        this.address = address;
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
        this.addedDate = addedDate;
        return this;
    }

    public ParticipantDetailsBuilder setAddedBy(final String addedBy) {
        this.addedBy = addedBy;
        return this;
    }

    public ParticipantDetailsBuilder setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public ParticipantDetailsBuilder setUpdatedBy(final String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public ParticipantDetails createParticipantDetails() {
        return ParticipantDetails.create(participantId, name, address, email, contactNumber, addedDate, addedBy, updatedDate, updatedBy);
    }
}