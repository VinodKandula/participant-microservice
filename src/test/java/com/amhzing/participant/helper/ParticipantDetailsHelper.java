package com.amhzing.participant.helper;

import com.amhzing.participant.query.data.cassandra.mapping.ParticipantDetails;
import com.amhzing.participant.query.data.cassandra.mapping.ParticipantDetailsBuilder;

import java.util.Date;

public final class ParticipantDetailsHelper {

    private ParticipantDetailsHelper() {
        // To prevent instantiation
    }

    public static ParticipantDetails participantDetailsBuilder(final Date date) {
        return new ParticipantDetailsBuilder().setFirstName(firstName())
                                              .setMiddleName(middleName())
                                              .setLastName(lastName())
                                              .setSuffix(suffix())
                                              .setAddressLine1(addressLine1())
                                              .setAddressLine2(addressLine2())
                                              .setCity(city())
                                              .setCountry(country())
                                              .setPostalCode(postalCode())
                                              .setEmail(email())
                                              .setContactNumber(contactNumber())
                                              .setAddedDate(date)
                                              .setAddedBy(userId())
                                              .setUpdatedDate(date)
                                              .setUpdatedBy(userId())
                                              .create();
    }

    public static String userId() {
        return "myUser";
    }

    public static String contactNumber() {
        return "123456789";
    }

    public static String email() {
        return "test@example.com";
    }

    public static String postalCode() {
        return "19337";
    }

    public static String country() {
        return "SE";
    }

    public static String city() {
        return "C1";
    }

    public static String addressLine1() {
        return "Ad1";
    }

    public static String addressLine2() {
        return "Ad2";
    }

    public static String suffix() {
        return "IV";
    }

    public static String lastName() {
        return "Doe";
    }

    public static String middleName() {
        return "D";
    }

    public static String firstName() {
        return "John";
    }
}
