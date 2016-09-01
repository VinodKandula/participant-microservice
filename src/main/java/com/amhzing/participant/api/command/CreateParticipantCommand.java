package com.amhzing.participant.api.command;

import com.amhzing.participant.api.model.Address;
import com.amhzing.participant.api.model.ContactNumber;
import com.amhzing.participant.api.model.Email;
import com.amhzing.participant.api.model.Name;

import java.util.UUID;

import static org.apache.commons.lang3.Validate.notNull;

public class CreateParticipantCommand extends AbstractParticipantCommand {

    private Name name;
    private Address address;
    private ContactNumber contactNumber;
    private Email email;

    private CreateParticipantCommand() {

    }

    private CreateParticipantCommand(final UUID id,
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

    public static CreateParticipantCommand create(final UUID id,
                                                  final Name name,
                                                  final Address address,
                                                  final ContactNumber contactNumber,
                                                  final Email email) {
        return new CreateParticipantCommand(id, name, address, contactNumber, email);
    }

    public static CreateParticipantCommand empty() {
        return new CreateParticipantCommand();
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
