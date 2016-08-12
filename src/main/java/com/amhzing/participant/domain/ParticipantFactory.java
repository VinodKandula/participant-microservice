package com.amhzing.participant.domain;

import com.amhzing.participant.api.event.ParticipantCreatedEvent;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

class ParticipantFactory{
    private ParticipantFactory() {
    }

    public static Name createNameFrom(final ParticipantCreatedEvent event) {
        final String firstName = event.getName().getFirstName();
        final String middleName = event.getName().getMiddleName();
        final String lastName = event.getName().getLastName();
        final String suffix = event.getName().getSuffix();

        return Name.create(isNotEmpty(firstName) ? FirstName.create(firstName) : null,
                           isNotEmpty(middleName) ? MiddleName.create(middleName) : null,
                           isNotEmpty(lastName) ? LastName.create(lastName) : null,
                           isNotEmpty(suffix) ? Suffix.create(suffix) : null);
    }

    public static Address createAddressFrom(final ParticipantCreatedEvent event) {
        final String addressLine1 = event.getAddress().getAddressLine1();
        final String addressLine2 = event.getAddress().getAddressLine2();
        final String city = event.getAddress().getCity();
        final String postalCode = event.getAddress().getPostalCode();
        final String countryCode = event.getAddress().getCountry().getCode();
        final String countryName = event.getAddress().getCountry().getName();

        return Address.create(isNotEmpty(addressLine1) ? AddressLine1.create(addressLine1) : null,
                              isNotEmpty(addressLine2) ? AddressLine2.create(addressLine2) : null,
                              isNotEmpty(city) ? City.create(city) : null,
                              isNotEmpty(postalCode) ? PostalCode.create(postalCode) : null,
                              isNotEmpty(countryCode) ? Country.create(countryCode, countryName) : null);
    }

    public static ContactNumber createContactNumberFrom(final ParticipantCreatedEvent event) {
        final String value = event.getContactNumber().getValue();

        return isNotEmpty(value) ? ContactNumber.create(value) : null;
    }

    public static Email createEmailFrom(final ParticipantCreatedEvent event) {
        final String value = event.getEmail().getValue();

        return isNotEmpty(value) ? Email.create(value) : null;
    }
}