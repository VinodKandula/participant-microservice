package com.amhzing.participant.domain;

import com.amhzing.participant.api.event.ParticipantCreatedEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;

public class MiddleName extends AbstractAnnotatedEntity {

    private static final Logger LOGGER = LoggerFactory.getLogger(MiddleName.class);

    static final int MAX_LENGTH = 25;

    private String value;

    private MiddleName(final String value) {
        isValid(value);

        this.value = value.trim();
    }

    public static MiddleName create(final String value) {
        return new MiddleName(value);
    }

    @EventHandler
    public void on(final ParticipantCreatedEvent event) {
        final String middleName = event.getName().getMiddleName();
        if (isValid(middleName)) {
            this.value = middleName;
            LOGGER.debug("Applied ParticipantCreatedEvent [{}] for middle name '{}'", event.getId(), middleName);
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MiddleName that = (MiddleName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "MiddleName{" +
                "value='" + value + '\'' +
                '}';
    }

    private boolean isValid(final String value) {
        notBlank(value);
        isTrue(value.length() <= MAX_LENGTH);

        return true;
    }
}
