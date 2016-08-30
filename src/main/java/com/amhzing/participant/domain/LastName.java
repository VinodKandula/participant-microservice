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

public class LastName extends AbstractAnnotatedEntity {

    private static final Logger LOGGER = LoggerFactory.getLogger(LastName.class);

    static final int MAX_LENGTH = 25;

    private String value;

    private LastName(final String value) {
        isValid(value);

        this.value = trim(value);
    }

    public static LastName create(final String value) {
        return new LastName(value);
    }

    @EventHandler
    public void handleEvent(final ParticipantCreatedEvent event) {
        final String lastName = event.getName().getLastName();
        if (isValid(lastName)) {
            this.value = lastName;
        } else {
            LOGGER.info("Invalid last name >{}<", lastName);
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LastName lastName = (LastName) o;
        return Objects.equals(value, lastName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "LastName{" +
                "value='" + value + '\'' +
                '}';
    }

    private boolean isValid(final String value) {
        notBlank(value);
        isTrue(value.length() <= MAX_LENGTH);

        return true;
    }
}
