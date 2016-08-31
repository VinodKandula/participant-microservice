package com.amhzing.participant.api.request;

import com.amhzing.participant.api.model.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@JsonInclude
public class CreateParticipantRequest {

    @NotNull @Valid
    private final Name name;

    @NotNull @Valid
    private final Address address;

    @Valid
    private final ContactNumber contactNumber;

    @Valid
    private final Email email;

    @NotNull @Valid
    private final User user;

    private CreateParticipantRequest(final Name name,
                                     final Address address,
                                     final ContactNumber contactNumber,
                                     final Email email,
                                     final User user) {
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.email = email;
        this.user = user;
    }

    @JsonCreator
    public static CreateParticipantRequest create(@JsonProperty("name") final Name name,
                                                  @JsonProperty("address") final Address address,
                                                  @JsonProperty("contactNumber") final ContactNumber contactNumber,
                                                  @JsonProperty("email") final Email email,
                                                  @JsonProperty("user") final User user) {
        return new CreateParticipantRequest(name, address, contactNumber, email, user);
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

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CreateParticipantRequest that = (CreateParticipantRequest) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(address, that.address) &&
                Objects.equals(contactNumber, that.contactNumber) &&
                Objects.equals(email, that.email) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, contactNumber, email, user);
    }

    @Override
    public String toString() {
        return "CreateParticipantRequest{" +
                "name=" + name +
                ", address=" + address +
                ", contactNumber=" + contactNumber +
                ", email=" + email +
                ", user=" + user +
                '}';
    }
}
