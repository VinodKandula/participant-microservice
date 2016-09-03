package com.amhzing.participant.command.domain;

import com.amhzing.participant.api.event.ParticipantCreatedEvent;
import org.axonframework.domain.MetaData;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import java.io.Serializable;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public class Address extends AbstractAnnotatedEntity implements Serializable {

    private AddressLine1 addressLine1;
    private AddressLine2 addressLine2;
    private City city;
    private PostalCode postalCode;
    private Country country;

    private Address(final AddressLine1 addressLine1,
                    final AddressLine2 addressLine2,
                    final City city,
                    final PostalCode postalCode,
                    final Country country) {
        this.addressLine1 = notNull(addressLine1);
        this.addressLine2 = addressLine2;
        this.city = notNull(city);
        this.postalCode = postalCode;
        this.country = notNull(country);
    }

    public static Address create(final AddressLine1 addressLine1,
                                  final AddressLine2 addressLine2,
                                  final City city,
                                  final PostalCode postalCode,
                                  final Country country) {
        return new Address(addressLine1, addressLine2, city, postalCode, country);
    }

    @EventSourcingHandler
    public void handleEvent(final ParticipantCreatedEvent event, final MetaData metadata) {
        notNull(event.getAddress());

        // Include any business logic regarding the event here
    }

    public AddressLine1 getAddressLine1() {
        return addressLine1;
    }

    public AddressLine2 getAddressLine2() {
        return addressLine2;
    }

    public City getCity() {
        return city;
    }

    public PostalCode getPostalCode() {
        return postalCode;
    }

    public Country getCountry() {
        return country;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Address address = (Address) o;
        return Objects.equals(addressLine1, address.addressLine1) &&
                Objects.equals(addressLine2, address.addressLine2) &&
                Objects.equals(city, address.city) &&
                Objects.equals(postalCode, address.postalCode) &&
                Objects.equals(country, address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressLine1, addressLine2, city, postalCode, country);
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressLine1=" + addressLine1 +
                ", addressLine2=" + addressLine2 +
                ", city=" + city +
                ", postalCode=" + postalCode +
                ", country=" + country +
                '}';
    }
}
