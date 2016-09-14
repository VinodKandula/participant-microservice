package com.amhzing.participant.query.eventhandler;

import com.amhzing.participant.annotation.Online;
import com.amhzing.participant.api.event.ParticipantCreatedEvent;
import com.amhzing.participant.query.data.cassandra.mapping.*;
import com.amhzing.participant.query.exception.QueryInsertException;
import com.datastax.driver.core.querybuilder.Batch;
import com.datastax.driver.core.querybuilder.Insert;
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

import static com.datastax.driver.core.querybuilder.QueryBuilder.batch;
import static org.apache.commons.lang.Validate.notNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.lowerCase;

@Online
@Component
public class ParticipantCreatedEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantCreatedEventHandler.class);

    private final CassandraTemplate cassandraTemplate;

    // This is auto-configured by Spring Boot
    @Autowired
    public ParticipantCreatedEventHandler(final CassandraTemplate cassandraTemplate) {
        this.cassandraTemplate = cassandraTemplate;
    }

    @EventHandler
    public void handleEvent(final ParticipantCreatedEvent event, final MetaData metadata) {
        notNull(event);
        notNull(metadata);

        try {
            LOGGER.info("Inserting {} details for participant {}", ParticipantCreatedEvent.class.getSimpleName(), event.getId());
            final Batch batch = batch(insertIntoByCountry(event, metadata),
                                      insertIntoById(event, metadata));
            cassandraTemplate.execute(batch);
        } catch (final Exception ex) {
            LOGGER.error("Failed to insert {}", event);
            throw new QueryInsertException("Failed to insert event: " + event.getId(), ex);
        }
    }

    private Insert insertIntoById(final ParticipantCreatedEvent event, final MetaData metadata) {
        return cassandraTemplate.createInsertQuery(cassandraTemplate.getTableName(ParticipantDetailsById.class).toCql(),
                                                   participantDetailsById(event, metadata),
                                                   null,
                                                   cassandraTemplate.getConverter());
    }

    private Insert insertIntoByCountry(final ParticipantCreatedEvent event, final MetaData metadata) {
        return cassandraTemplate.createInsertQuery(cassandraTemplate.getTableName(ParticipantDetailsByCountry.class).toCql(),
                                                   participantDetailsByCountry(event, metadata),
                                                   null,
                                                   cassandraTemplate.getConverter());
    }

    private ParticipantDetailsByCountry participantDetailsByCountry(final ParticipantCreatedEvent event,
                                                                    final MetaData metadata) {
        return new ParticipantDetailsByCountryBuilder().setPrimaryKey(primaryKey(event))
                                                       .setParticipantDetails(participantDetails(event, metadata))
                                                       .create();
    }

    private ParticipantDetailsById participantDetailsById(final ParticipantCreatedEvent event,
                                                          final MetaData metadata) {
        return new ParticipantDetailsByIdBuilder().setParticipantId(event.getId())
                                                  .setParticipantDetails(participantDetails(event, metadata))
                                                  .create();
    }

    private ParticipantDetails participantDetails(final ParticipantCreatedEvent event,
                                                  final MetaData metadata) {
        return new ParticipantDetailsBuilder().setFirstName(event.getName().getFirstName())
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
        return new ParticipantPrimaryKeyBuilder().setCountry(lowerCase(event.getAddress().getCountry().getCode()))
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
