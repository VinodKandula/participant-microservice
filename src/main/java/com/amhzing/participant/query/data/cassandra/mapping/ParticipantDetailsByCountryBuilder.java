package com.amhzing.participant.query.data.cassandra.mapping;

public class ParticipantDetailsByCountryBuilder {
    private ParticipantPrimaryKey primaryKey;
    private ParticipantDetails participantDetails;

    public ParticipantDetailsByCountryBuilder setParticipantDetails(final ParticipantDetails participantDetails) {
        this.participantDetails = participantDetails;
        return this;
    }

    public ParticipantDetailsByCountryBuilder setPrimaryKey(final ParticipantPrimaryKey primaryKey) {
        this.primaryKey = primaryKey;
        return this;
    }

    public ParticipantDetailsByCountry create() {
        return ParticipantDetailsByCountry.create(participantDetails.getFirstName(), participantDetails.getMiddleName(),
                                                  participantDetails.getLastName(), participantDetails.getSuffix(),
                                                  participantDetails.getAddressLine1(), participantDetails.getAddressLine2(),
                                                  participantDetails.getCity(), participantDetails.getCountry(),
                                                  participantDetails.getPostalCode(), participantDetails.getEmail(),
                                                  participantDetails.getContactNumber(), participantDetails.getAddedDate(),
                                                  participantDetails.getAddedBy(), participantDetails.getUpdatedDate(),
                                                  participantDetails.getUpdatedBy(), primaryKey);
    }
}