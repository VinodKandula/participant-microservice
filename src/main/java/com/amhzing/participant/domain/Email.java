package com.amhzing.participant.domain;

import com.amhzing.participant.api.event.ParticipantCreatedEvent;
import org.apache.commons.validator.routines.EmailValidator;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;

public class Email extends AbstractAnnotatedEntity {

    private static final Logger LOGGER = LoggerFactory.getLogger(Email.class);

    static final int MAX_LENGTH = 50;

    private String value;

    private Email(final String value) {
        isValid(value);

        this.value = value.trim();
    }

    public static Email create(final String value) {
        return new Email(value);
    }

    @EventHandler
    public void handleEvent(final ParticipantCreatedEvent event) {
        final String email = event.getEmail().getValue();
        if (isValid(email)) {
            this.value = email;
        } else {
            LOGGER.info("Invalid email >{}<", email);
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Email{" +
                "value='" + value + '\'' +
                '}';
    }

    private boolean isValid(final String value) {
        notBlank(value);
        isTrue(value.length() <= MAX_LENGTH);
        isTrue(EmailValidator.getInstance().isValid(value));

        return true;
    }
}
