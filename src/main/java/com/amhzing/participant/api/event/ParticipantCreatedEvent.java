package com.amhzing.participant.api.event;

import com.amhzing.participant.api.ContactNumber;
import com.amhzing.participant.api.Email;
import com.amhzing.participant.api.Name;

import java.util.UUID;

public class ParticipantCreatedEvent extends AbstractParticipantEvent {

    private Name name;
    private ContactNumber contactNumber;
    private Email email;

    public ParticipantCreatedEvent() {
    }

    public ParticipantCreatedEvent(final UUID id,
                                   final Name name,
                                   final ContactNumber contactNumber,
                                   final Email email) {
        super(id);
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public Name getName() {
        return name;
    }

    public ContactNumber getContactNumber() {
        return contactNumber;
    }

    public Email getEmail() {
        return email;
    }
}
