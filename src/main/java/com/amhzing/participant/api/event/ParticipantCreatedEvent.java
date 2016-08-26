package com.amhzing.participant.api.event;

import com.amhzing.participant.api.model.Address;
import com.amhzing.participant.api.model.ContactNumber;
import com.amhzing.participant.api.model.Email;
import com.amhzing.participant.api.model.Name;

import java.util.UUID;

import static org.apache.commons.lang3.Validate.notNull;

public class ParticipantCreatedEvent extends AbstractParticipantEvent {

    private Name name;
    private Address address;
    private ContactNumber contactNumber;
    private Email email;

    public ParticipantCreatedEvent() {
    }

    private ParticipantCreatedEvent(final UUID id,
                                    final Name name,
                                    final Address address,
                                    final ContactNumber contactNumber,
                                    final Email email) {
        super(id);
        this.name = notNull(name);
        this.address = notNull(address);
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public static ParticipantCreatedEvent create(final UUID id,
                                                 final Name name,
                                                 final Address address,
                                                 final ContactNumber contactNumber,
                                                 final Email email) {
        return new ParticipantCreatedEvent(id, name, address, contactNumber, email);
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
}
