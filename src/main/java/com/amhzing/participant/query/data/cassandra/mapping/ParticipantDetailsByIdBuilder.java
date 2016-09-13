package com.amhzing.participant.query.data.cassandra.mapping;

import java.util.UUID;

public class ParticipantDetailsByIdBuilder {
    private UUID participantId;
    private ParticipantDetails participantDetails;

    public ParticipantDetailsByIdBuilder setParticipantDetails(final ParticipantDetails participantDetails) {
        this.participantDetails = participantDetails;
        return this;
    }

    public ParticipantDetailsByIdBuilder setParticipantId(final UUID participantId) {
        this.participantId = participantId;
        return this;
    }

    public ParticipantDetailsById create() {
        return ParticipantDetailsById.create(participantDetails.getFirstName(), participantDetails.getMiddleName(),
                                             participantDetails.getLastName(), participantDetails.getSuffix(),
                                             participantDetails.getAddressLine1(), participantDetails.getAddressLine2(),
                                             participantDetails.getCity(), participantDetails.getCountry(),
                                             participantDetails.getPostalCode(), participantDetails.getEmail(),
                                             participantDetails.getContactNumber(), participantDetails.getAddedDate(),
                                             participantDetails.getAddedBy(), participantDetails.getUpdatedDate(),
                                             participantDetails.getUpdatedBy(), participantId);
    }
}