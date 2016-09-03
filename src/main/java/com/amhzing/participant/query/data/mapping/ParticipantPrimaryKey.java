package com.amhzing.participant.query.data.mapping;

import org.springframework.cassandra.core.Ordering;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import static org.apache.commons.lang3.Validate.notBlank;

@PrimaryKeyClass
public class ParticipantPrimaryKey implements Serializable {

    public static final String COUNTRY_COL = "country";
    public static final String CITY_COL = "city";
    public static final String ADDRESS_LINE_1_COL = "address_line1";
    public static final String LAST_NAME_COL = "last_name";
    public static final String PARTICIPANT_ID_COL = "participant_id";

    @PrimaryKeyColumn(ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String country;

    @PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private String city;

    @PrimaryKeyColumn(name = "address_line1", ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private String addressLine1;

    @PrimaryKeyColumn(name = "last_name", ordinal = 3, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private String lastName;

    @PrimaryKeyColumn(name = "participant_id", ordinal = 4, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private UUID participantId;

    private ParticipantPrimaryKey(final String country, final String city, final String addressLine1,
                                  final String lastName, final UUID participantId) {
        this.country = notBlank(country);
        this.city = city;
        this.addressLine1 = addressLine1;
        this.lastName = lastName;
        this.participantId = participantId;
    }

    public static ParticipantPrimaryKey create(final String country, final String city, final String addressLine1,
                                               final String lastName, final UUID participantId) {
        return new ParticipantPrimaryKey(country, city, addressLine1, lastName, participantId);
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getLastName() {
        return lastName;
    }

    public UUID getParticipantId() {
        return participantId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ParticipantPrimaryKey that = (ParticipantPrimaryKey) o;
        return Objects.equals(country, that.country) &&
                Objects.equals(city, that.city) &&
                Objects.equals(addressLine1, that.addressLine1) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(participantId, that.participantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, addressLine1, lastName, participantId);
    }

    @Override
    public String toString() {
        return "ParticipantPrimaryKey{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", lastName='" + lastName + '\'' +
                ", participantId=" + participantId +
                '}';
    }
}
