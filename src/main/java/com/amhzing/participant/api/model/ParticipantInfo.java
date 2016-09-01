package com.amhzing.participant.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@JsonInclude
public class ParticipantInfo implements Serializable {

    @NotBlank
    @JsonProperty("participantId")
    private final String participantId;

    @NotNull
    @JsonProperty("name")
    private final Name name;

    @NotNull
    @JsonProperty("address")
    private final Address address;

    @JsonProperty("contactNumber")
    private final ContactNumber contactNumber;

    @JsonProperty("email")
    private final Email email;

    private ParticipantInfo(final String participantId, final Name name, final Address address,
                            final ContactNumber contactNumber, final Email email) {
        this.participantId = participantId;
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public static ParticipantInfo create(final String participantId, final Name name, final Address address,
                                         final ContactNumber contactNumber, final Email email) {
        return new ParticipantInfo(participantId, name, address, contactNumber, email);
    }

    public String getParticipantId() {
        return participantId;
    }

    public Name getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public ContactNumber getContactNumber() {
        return contactNumber;
    }

    public Email getEmail() {
        return email;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ParticipantInfo that = (ParticipantInfo) o;
        return Objects.equals(participantId, that.participantId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address) &&
                Objects.equals(contactNumber, that.contactNumber) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participantId, name, address, contactNumber, email);
    }

    @Override
    public String toString() {
        return "ParticipantInfo{" +
                "participantId='" + participantId + '\'' +
                ", name=" + name +
                ", address=" + address +
                ", contactNumber=" + contactNumber +
                ", email=" + email +
                '}';
    }
}
