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

public class ContactNumber extends AbstractAnnotatedEntity {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactNumber.class);

    static final int MAX_LENGTH = 25;

    private String value;

    private ContactNumber(final String value) {
        isValid(value);

        this.value = trim(value);
    }

    public static ContactNumber create(final String value) {
        return new ContactNumber(value);
    }

    @EventHandler
    public void handleEvent(final ParticipantCreatedEvent event) {
        final String contactNumber = event.getContactNumber().orElseThrow(() -> new IllegalStateException("Contact number must have a value")).getValue();
        if (isValid(contactNumber)) {
            this.value = contactNumber;
        } else {
            LOGGER.info("Invalid contact number >{}<", contactNumber);
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ContactNumber that = (ContactNumber) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "ContactNumber{" +
                "value='" + value + '\'' +
                '}';
    }

    private boolean isValid(final String value) {
        notBlank(value);
        isTrue(value.length() <= MAX_LENGTH);

        return true;
    }
}
