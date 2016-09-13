package com.amhzing.participant.query.data.cassandra.mapping;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.util.Date;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

@Table(value = "participant_details_by_country")
public class ParticipantDetailsByCountry extends ParticipantDetails {

    @PrimaryKey
    private ParticipantPrimaryKey primaryKey;

    private ParticipantDetailsByCountry(final String firstName, final String middleName, final String lastName,
                                        final String suffix, final String addressLine1, final String addressLine2,
                                        final String city, final String country, final String postalCode,
                                        final String email, final String contactNumber, final Date addedDate,
                                        final String addedBy, final Date updatedDate, final String updatedBy,
                                        final ParticipantPrimaryKey primaryKey) {
        super(firstName, middleName, lastName, suffix, addressLine1, addressLine2, city, country, postalCode, email,
              contactNumber, addedDate, addedBy, updatedDate, updatedBy);
        this.primaryKey = notNull(primaryKey);
    }

    public static ParticipantDetailsByCountry create(final String firstName, final String middleName, final String lastName,
                                                     final String suffix, final String addressLine1, final String addressLine2,
                                                     final String city, final String country, final String postalCode,
                                                     final String email, final String contactNumber, final Date addedDate,
                                                     final String addedBy, final Date updatedDate, final String updatedBy,
                                                     final ParticipantPrimaryKey primaryKey) {
        return new ParticipantDetailsByCountry(firstName, middleName, lastName, suffix, addressLine1, addressLine2, city,
                                               country, postalCode, email, contactNumber, addedDate, addedBy, updatedDate,
                                               updatedBy, primaryKey);
    }

    public ParticipantPrimaryKey getPrimaryKey() {
        return primaryKey;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        final ParticipantDetailsByCountry that = (ParticipantDetailsByCountry) o;
        return Objects.equals(primaryKey, that.primaryKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), primaryKey);
    }

    @Override
    public String toString() {
        return "ParticipantDetailsByCountry{" +
                "primaryKey=" + primaryKey +
                "} " + super.toString();
    }
}
