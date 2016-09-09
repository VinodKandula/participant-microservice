package com.amhzing.participant.query.eventhandler;

import com.amhzing.participant.annotation.Offline;
import com.amhzing.participant.api.event.ParticipantCreatedEvent;
import com.amhzing.participant.query.data.jpa.mapping.*;
import com.amhzing.participant.query.data.jpa.repository.ParticipantRepository;
import com.amhzing.participant.query.exception.QueryInsertException;
import org.apache.commons.collections.MapUtils;
import org.axonframework.domain.MetaData;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.apache.commons.lang.Validate.notNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Offline
@Component
public class InMemParticipantCreatedEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(InMemParticipantCreatedEventHandler.class);

    @Autowired
    ParticipantRepository repository;

    @EventHandler
    public void handleEvent(final ParticipantCreatedEvent event, final MetaData metadata) {
        notNull(event);
        notNull(metadata);

        try {
            LOGGER.info("Inserting {} details for participant {}", ParticipantCreatedEvent.class.getSimpleName(), event.getId());
            repository.save(participantDetails(event, metadata));
        } catch (final Exception ex) {
            LOGGER.error("Failed to insert {}", event);
            throw new QueryInsertException("Failed to insert event: " + event.getId(), ex);
        }
    }

    private ParticipantDetails participantDetails(final ParticipantCreatedEvent event, final MetaData metadata) {
        final ParticipantDetailsBuilder participantDetailsBuilder = new ParticipantDetailsBuilder();
        participantDetailsBuilder.setParticipantId(event.getId().toString());
        participantDetailsBuilder.setName(buildName(event));
        participantDetailsBuilder.setAddress(buildAddress(event));
        participantDetailsBuilder.setEmail(email(event));
        participantDetailsBuilder.setContactNumber(contactNumber(event));
        participantDetailsBuilder.setAddedDate(currentTime());
        participantDetailsBuilder.setAddedBy(userId(metadata));
        participantDetailsBuilder.setUpdatedDate(currentTime());
        participantDetailsBuilder.setUpdatedBy(userId(metadata));

        return participantDetailsBuilder.createParticipantDetails();
    }

    private Name buildName(final ParticipantCreatedEvent event) {
        return new NameBuilder().setFirstName(event.getName().getFirstName())
                                .setMiddleName(event.getName().getMiddleName())
                                .setLastName(event.getName().getLastName())
                                .setSuffix(event.getName().getSuffix())
                                .createName();
    }

    private Address buildAddress(final ParticipantCreatedEvent event) {
        return new AddressBuilder().setAddressLine1(event.getAddress().getAddressLine1())
                                   .setAddressLine2(event.getAddress().getAddressLine2())
                                   .setCity(event.getAddress().getCity())
                                   .setPostalCode(event.getAddress().getPostalCode())
                                   .setCountry(event.getAddress().getCountry().getCode())
                                   .createAddress();
    }

    private Timestamp currentTime() {
        final ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        return Timestamp.from(utc.toInstant());
    }

    public static String contactNumber(final ParticipantCreatedEvent event) {
        if (event.getContactNumber() != null && isNotBlank(event.getContactNumber().getValue())) {
            return event.getContactNumber().getValue();
        }
        return null;
    }

    public static String email(final ParticipantCreatedEvent event) {
        if (event.getEmail() != null && isNotBlank(event.getEmail().getValue())) {
            return event.getEmail().getValue();
        }
        return null;
    }

    private String userId(final MetaData metadata) {
        return MapUtils.getString(metadata, "userId", "UNKNOWN");
    }
}
