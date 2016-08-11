package com.amhzing.participant.domain;

import com.amhzing.participant.api.event.ParticipantCreatedEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static com.sun.corba.se.spi.activation.IIOP_CLEAR_TEXT.value;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.bouncycastle.asn1.x500.style.RFC4519Style.postalCode;

public class Country extends AbstractAnnotatedEntity {

    private static final Logger LOGGER = LoggerFactory.getLogger(Country.class);

    static final int MIN_CODE_LENGTH = 2;
    static final int MAX_CODE_LENGTH = 3;
    static final int MAX_NAME_LENGTH = 100;

    private String code;
    private String name;

    private Country(final String code, final String name) {
        isValidCode(code);
        isValidName(name);

        this.code = value.trim();
        this.name = name.trim();
    }

    public static Country create(final String code, final String name) {
        return new Country(code, name);
    }

    @EventHandler
    public void handleEvent(final ParticipantCreatedEvent event) {
        final String code = event.getAddress().getCountry().getCode();
        final String name = event.getAddress().getCountry().getName();
        if (isValidCode(code) && isValidName(name)) {
            this.code = code;
            this.name = name;
        } else {
            LOGGER.info("Invalid code >{}< or name >{}<", code, name);
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Country country = (Country) o;
        return Objects.equals(code, country.code) &&
                Objects.equals(name, country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name);
    }

    @Override
    public String toString() {
        return "Country{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    private boolean isValidCode(final String value) {
        notBlank(value);
        isTrue(value.length() >= MIN_CODE_LENGTH && value.length() <= MAX_CODE_LENGTH);

        return true;
    }

    private boolean isValidName(final String value) {
        notBlank(value);
        isTrue(value.length() <= MAX_NAME_LENGTH);

        return true;
    }
}
