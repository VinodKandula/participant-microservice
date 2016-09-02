package com.amhzing.participant.web.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Objects;

@JsonInclude
public class QueryParticipantRequest {

    @NotBlank
    private String country;
    private String city;
    private String addressLine1;
    private String lastName;
    private String participantId;

    private QueryParticipantRequest(final String country, final String city, final String addressLine1,
                                    final String lastName, final String participantId) {
        this.country = country;
        this.city = city;
        this.addressLine1 = addressLine1;
        this.lastName = lastName;
        this.participantId = participantId;
    }

    @JsonCreator
    public static QueryParticipantRequest create(@JsonProperty("country") final String country,
                                                 @JsonProperty("city") final String city,
                                                 @JsonProperty("addressLine1") final String addressLine1,
                                                 @JsonProperty("lastName")final String lastName,
                                                 @JsonProperty("participantId") final String participantId) {
        return new QueryParticipantRequest(country, city, addressLine1, lastName, participantId);
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

    public String getParticipantId() {
        return participantId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final QueryParticipantRequest that = (QueryParticipantRequest) o;
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
        return "QueryParticipantRequest{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", lastName='" + lastName + '\'' +
                ", participantId='" + participantId + '\'' +
                '}';
    }
}
