package com.amhzing.participant.domain;

import com.amhzing.participant.api.event.ParticipantCreatedEvent;

import static org.apache.commons.lang.StringUtils.isNotBlank;

class ParticipantFactory{
    private ParticipantFactory() {
    }

    public static Name createNameFrom(final ParticipantCreatedEvent event) {
        final String firstName = event.getName().getFirstName();
        final String middleName = event.getName().getMiddleName();
        final String lastName = event.getName().getLastName();
        final String suffix = event.getName().getSuffix();

        return Name.create(isNotBlank(firstName) ? FirstName.create(firstName) : null,
                           isNotBlank(middleName) ? MiddleName.create(middleName) : null,
                           isNotBlank(lastName) ? LastName.create(lastName) : null,
                           isNotBlank(suffix) ? Suffix.create(suffix) : null);
    }

    public static Address createAddressFrom(final ParticipantCreatedEvent event) {
        final String addressLine1 = event.getAddress().getAddressLine1();
        final String addressLine2 = event.getAddress().getAddressLine2();
        final String city = event.getAddress().getCity();
        final String postalCode = event.getAddress().getPostalCode();
        final String countryCode = event.getAddress().getCountry().getCode();
        final String countryName = event.getAddress().getCountry().getName();

        return Address.create(isNotBlank(addressLine1) ? AddressLine1.create(addressLine1) : null,
                              isNotBlank(addressLine2) ? AddressLine2.create(addressLine2) : null,
                              isNotBlank(city) ? City.create(city) : null,
                              isNotBlank(postalCode) ? PostalCode.create(postalCode) : null,
                              isNotBlank(countryCode) ? Country.create(countryCode, countryName) : null);
    }

    public static ContactNumber createContactNumberFrom(final ParticipantCreatedEvent event) {
        if (event.getContactNumber().isPresent()) {
            ContactNumber.create(event.getContactNumber().get().getValue());
        }
        return null;
    }

    public static Email createEmailFrom(final ParticipantCreatedEvent event) {
        if (event.getEmail().isPresent()) {
            Email.create(event.getEmail().get().getValue());
        }
        return null;
    }
}