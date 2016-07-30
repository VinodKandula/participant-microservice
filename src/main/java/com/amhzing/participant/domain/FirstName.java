package com.amhzing.participant.domain;

import com.amhzing.participant.api.event.ParticipantCreatedEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;

public class FirstName extends AbstractAnnotatedEntity {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirstName.class);

    static final int MAX_LENGTH = 25;

    private String value;

    private FirstName(final String value) {
        isValid(value);

        this.value = value.trim();
    }

    public static FirstName create(final String value) {
        return new FirstName(value);
    }

    @EventHandler
    public void on(final ParticipantCreatedEvent event) {
        final String firstName = event.getName().getFirstName();
        if (isValid(firstName)) {
            this.value = firstName;
            System.out.println("Applied ParticipantCreatedEvent " + firstName + "for first name " + event.getId());
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FirstName firstName = (FirstName) o;
        return Objects.equals(value, firstName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "FirstName{" +
                "value='" + value + '\'' +
                '}';
    }

    private boolean isValid(final String value) {
        notBlank(value);
        isTrue(value.length() <= MAX_LENGTH);

        return true;
    }
}
