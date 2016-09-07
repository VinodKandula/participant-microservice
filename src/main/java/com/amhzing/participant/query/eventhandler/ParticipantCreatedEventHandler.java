package com.amhzing.participant.query.eventhandler;

import com.amhzing.participant.annotation.Online;
import com.amhzing.participant.api.event.ParticipantCreatedEvent;
import com.amhzing.participant.query.data.cassandra.mapping.ParticipantDetails;
import com.amhzing.participant.query.data.cassandra.mapping.ParticipantDetailsBuilder;
import com.amhzing.participant.query.data.cassandra.mapping.ParticipantPrimaryKey;
import com.amhzing.participant.query.data.cassandra.mapping.ParticipantPrimaryKeyBuilder;
import com.amhzing.participant.query.exception.QueryInsertException;
import org.apache.commons.collections.MapUtils;
import org.axonframework.domain.MetaData;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.apache.commons.lang.Validate.notNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.lowerCase;

@Online
@Component
public class ParticipantCreatedEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantCreatedEventHandler.class);

    // This is auto-configured by Spring Boot
    @Autowired
    CassandraTemplate cassandraTemplate;

    @EventHandler
    public void handleEvent(final ParticipantCreatedEvent event, final MetaData metadata) {
        notNull(event);
        notNull(metadata);

        try {
            LOGGER.info("Inserting {} details for participant {}", ParticipantCreatedEvent.class.getSimpleName(), event.getId());
            cassandraTemplate.insert(participantDetails(event, metadata));
        } catch (final Exception ex) {
            throw new QueryInsertException(ex);
        }
    }

    private ParticipantDetails participantDetails(final ParticipantCreatedEvent event, final MetaData metadata) {
        return new ParticipantDetailsBuilder().setPrimaryKey(primaryKey(event))
                                              .setFirstName(event.getName().getFirstName())
                                              .setMiddleName(event.getName().getMiddleName())
                                              .setLastName(event.getName().getLastName())
                                              .setSuffix(event.getName().getSuffix())
                                              .setAddressLine1(event.getAddress().getAddressLine1())
                                              .setAddressLine2(event.getAddress().getAddressLine2())
                                              .setCity(event.getAddress().getCity())
                                              .setCountry(event.getAddress().getCountry().getCode())
                                              .setPostalCode(event.getAddress().getPostalCode())
                                              .setEmail(email(event))
                                              .setContactNumber(contactNumber(event))
                                              .setAddedDate(currentTime())
                                              .setAddedBy(userId(metadata))
                                              .setUpdatedDate(currentTime())
                                              .setUpdatedBy(userId(metadata))
                                              .create();
    }

    private ParticipantPrimaryKey primaryKey(final ParticipantCreatedEvent event) {
        return new ParticipantPrimaryKeyBuilder()
                .setCountry(lowerCase(event.getAddress().getCountry().getCode()))
                .setCity(lowerCase(event.getAddress().getCity()))
                .setAddressLine1(lowerCase(event.getAddress().getAddressLine1()))
                .setLastName(lowerCase(event.getName().getLastName()))
                .setParticipantId(event.getId())
                .create();
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
