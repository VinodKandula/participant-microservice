package com.amhzing.participant.application.query.mapping;

import java.util.UUID;

public class ParticipantPrimaryKeyBuilder {
    private String country;
    private String city;
    private String addressLine1;
    private String lastName;
    private UUID participantId;

    public ParticipantPrimaryKeyBuilder setCountry(final String country) {
        this.country = country;
        return this;
    }

    public ParticipantPrimaryKeyBuilder setCity(final String city) {
        this.city = city;
        return this;
    }

    public ParticipantPrimaryKeyBuilder setAddressLine1(final String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public ParticipantPrimaryKeyBuilder setLastName(final String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ParticipantPrimaryKeyBuilder setParticipantId(final UUID participantId) {
        this.participantId = participantId;
        return this;
    }

    public ParticipantPrimaryKey create() {
        return ParticipantPrimaryKey.create(country, city, addressLine1, lastName, participantId);
    }
}