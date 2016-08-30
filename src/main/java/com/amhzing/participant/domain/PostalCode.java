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

public class PostalCode extends AbstractAnnotatedEntity {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostalCode.class);

    static final int MAX_LENGTH = 10;

    private String value;

    private PostalCode(final String value) {
        isValid(value);

        this.value = trim(value);
    }

    public static PostalCode create(final String value) {
        return new PostalCode(value);
    }

    @EventHandler
    public void handleEvent(final ParticipantCreatedEvent event) {
        final String postalCode = event.getAddress().getPostalCode();
        if (isValid(postalCode)) {
            this.value = postalCode;
        } else {
            LOGGER.info("Invalid postal code >{}<", postalCode);
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PostalCode that = (PostalCode) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "PostalCode{" +
                "value='" + value + '\'' +
                '}';
    }

    private boolean isValid(final String value) {
        notBlank(value);
        isTrue(value.length() <= MAX_LENGTH);

        return true;
    }
}
