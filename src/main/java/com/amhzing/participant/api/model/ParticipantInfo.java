package com.amhzing.participant.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@JsonInclude
public class ParticipantInfo {

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

    private ParticipantInfo(final Name name, final Address address,
                            final ContactNumber contactNumber, final Email email) {
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public static ParticipantInfo create(final Name name, final Address address,
                                         final ContactNumber contactNumber, final Email email) {
        return new ParticipantInfo(name, address, contactNumber, email);
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
        return Objects.equals(name, that.name) &&
                Objects.equals(address, that.address) &&
                Objects.equals(contactNumber, that.contactNumber) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, contactNumber, email);
    }

    @Override
    public String toString() {
        return "QueryParticipantResponse{" +
                "name=" + name +
                ", address=" + address +
                ", contactNumber=" + contactNumber +
                ", email=" + email +
                '}';
    }
}
