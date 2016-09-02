package com.amhzing.participant.application.command;

import com.amhzing.participant.api.model.*;

import java.util.Objects;

public class CreateParticipant {
    private final Name name;
    private final Address address;
    private final ContactNumber contactNumber;
    private final Email email;
    private final User user;

    private CreateParticipant(final Name name, final Address address, final ContactNumber contactNumber, final Email email, final User user) {
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.email = email;
        this.user = user;
    }

    public static CreateParticipant create(final Name name, final Address address, final ContactNumber contactNumber, final Email email, final User user) {
        return new CreateParticipant(name, address, contactNumber, email, user);
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
        final CreateParticipant that = (CreateParticipant) o;
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
        return "CreateParticipant{" +
                "name=" + name +
                ", address=" + address +
                ", contactNumber=" + contactNumber +
                ", email=" + email +
                ", user=" + user +
                '}';
    }
}
