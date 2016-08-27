package com.amhzing.participant.query;

import com.amhzing.participant.query.mapping.ParticipantDetails;
import com.datastax.driver.core.querybuilder.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.amhzing.participant.query.mapping.ParticipantPrimaryKey.*;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.apache.commons.lang3.Validate.notBlank;

@Component
public class QueryParticipantDetails {

    // This is auto-configured by Spring Boot
    @Autowired
    CassandraTemplate cassandraTemplate;

    public List<ParticipantDetails> participantDetails(final String country,
                                                       final String city,
                                                       final String addressLine1,
                                                       final String lastName,
                                                       final String participantId) {
        notBlank(country);

        final Select select = select().from("participant_details");
        final Select.Where where = select.where(eq(COUNTRY_COL, country));

        if (isNotBlank(city)) {
            where.and(eq(CITY_COL, city));
        }

        if (isNotBlank(addressLine1)) {
            where.and(eq(ADDRESS_LINE_1_COL, addressLine1));
        }

        if (isNotBlank(lastName)) {
            where.and(eq(LAST_NAME_COL, lastName));
        }

        if (isNotBlank(participantId)) {
            where.and(eq(PARTICIPANT_ID_COL, participantId));
        }

        return cassandraTemplate.select(select, ParticipantDetails.class);
    }
}
