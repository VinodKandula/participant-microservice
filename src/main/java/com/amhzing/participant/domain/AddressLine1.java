package com.amhzing.participant.domain;

import com.amhzing.participant.api.event.ParticipantCreatedEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.trim;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;

public class AddressLine1 extends AbstractAnnotatedEntity {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressLine1.class);

    static final int MAX_LENGTH = 25;

    private String value;

    private AddressLine1(final String value) {
        isValid(value);

        this.value = trim(value);
    }

    public static AddressLine1 create(final String value) {
        return new AddressLine1(value);
    }

    @EventHandler
    public void handleEvent(final ParticipantCreatedEvent event) {
        final String addressLine1 = event.getAddress().getAddressLine1();
        if (isValid(addressLine1)) {
            this.value = addressLine1;
        } else {
            LOGGER.info("Invalid address line 1 >{}<", addressLine1);
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AddressLine1 that = (AddressLine1) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "AddressLine1{" +
                "value='" + value + '\'' +
                '}';
    }

    private boolean isValid(final String value) {
        notBlank(value);
        isTrue(value.length() <= MAX_LENGTH);

        return true;
    }
}
