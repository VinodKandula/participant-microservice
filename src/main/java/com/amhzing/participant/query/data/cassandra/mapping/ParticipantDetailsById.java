package com.amhzing.participant.query.data.cassandra.mapping;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Table(value = "participant_details_by_id")
public class ParticipantDetailsById extends ParticipantDetails {

    @PrimaryKey("participant_id")
    private UUID participantId;

    private ParticipantDetailsById(final String firstName, final String middleName, final String lastName,
                                   final String suffix, final String addressLine1, final String addressLine2,
                                   final String city, final String country, final String postalCode,
                                   final String email, final String contactNumber, final Date addedDate,
                                   final String addedBy, final Date updatedDate, final String updatedBy,
                                   final UUID participantId) {
        super(firstName, middleName, lastName, suffix, addressLine1, addressLine2, city, country, postalCode, email,
              contactNumber, addedDate, addedBy, updatedDate, updatedBy);
        this.participantId = participantId;
    }

    public static ParticipantDetailsById create(final String firstName, final String middleName, final String lastName,
                                                final String suffix, final String addressLine1, final String addressLine2,
                                                final String city, final String country, final String postalCode,
                                                final String email, final String contactNumber, final Date addedDate,
                                                final String addedBy, final Date updatedDate, final String updatedBy,
                                                final UUID participantId) {
        return new ParticipantDetailsById(firstName, middleName, lastName, suffix, addressLine1, addressLine2, city, country, postalCode, email, contactNumber, addedDate, addedBy, updatedDate, updatedBy, participantId);
    }

    public UUID getParticipantId() {
        return participantId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        final ParticipantDetailsById that = (ParticipantDetailsById) o;
        return Objects.equals(participantId, that.participantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), participantId);
    }

    @Override
    public String toString() {
        return "ParticipantDetailsById{" +
                "participantId=" + participantId +
                "} " + super.toString();
    }
}
