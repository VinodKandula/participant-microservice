package com.amhzing.participant.helper;

import com.amhzing.participant.api.model.*;
import com.amhzing.participant.command.application.ParticipantToCreate;

public final class ParticipantApiModelHelper {

    private ParticipantApiModelHelper() {
        // To prevent instantiation
    }

    public static ParticipantToCreate participantToCreate() {
        return ParticipantToCreate.create(name(), address(), contactNumber(), email(), user());
    }

    public static Address address() {
        return Address.create("ad1", "ad2", "city", "pCode", Country.create("SE", ""));
    }

    public static Name name() {
        return Name.create("fname", "mName", "lName", "II");
    }

    public static ContactNumber contactNumber() {
        return ContactNumber.create("12345678");
    }

    public static Email email() {
        return Email.create("test@example.com");
    }

    public static User user() {
        return User.create("myUser");
    }
}
