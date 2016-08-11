package com.amhzing.participant.api.command;

import com.amhzing.participant.api.Address;
import com.amhzing.participant.api.ContactNumber;
import com.amhzing.participant.api.Email;
import com.amhzing.participant.api.Name;

import java.util.UUID;

import static org.apache.commons.lang3.Validate.notNull;

public class CreateParticipantCommand extends AbstractParticipantCommand {

    private final Name name;
    private Address address;
    private ContactNumber contactNumber;
    private Email email;

    public CreateParticipantCommand(final UUID id,
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
