package com.amhzing.participant.query.data;

import com.amhzing.participant.query.data.mapping.ParticipantDetails;
import com.amhzing.participant.query.data.mapping.ParticipantPrimaryKey;
import com.datastax.driver.core.querybuilder.Select;
import org.springframework.data.cassandra.core.CassandraTemplate;

import java.util.List;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;
import static org.apache.commons.lang.StringUtils.isNotBlank;

public class DefaultQueryParticipant implements QueryParticipant {

    private final CassandraTemplate cassandraTemplate;

    public DefaultQueryParticipant(final CassandraTemplate cassandraTemplate) {
        this.cassandraTemplate = cassandraTemplate;
    }

    @Override
    public List<ParticipantDetails> participantDetails(final QueryCriteria queryCriteria) {

        final Select select = select().from("participant_details");
        final Select.Where where = select.where(eq(ParticipantPrimaryKey.COUNTRY_COL, queryCriteria.getCountry()));

        if (isNotBlank(queryCriteria.getCity())) {
            where.and(eq(ParticipantPrimaryKey.CITY_COL, queryCriteria.getCity()));
        }

        if (isNotBlank(queryCriteria.getAddressLine1())) {
            where.and(eq(ParticipantPrimaryKey.ADDRESS_LINE_1_COL, queryCriteria.getAddressLine1()));
        }

        if (isNotBlank(queryCriteria.getLastName())) {
            where.and(eq(ParticipantPrimaryKey.LAST_NAME_COL, queryCriteria.getLastName()));
        }

        if (isNotBlank(queryCriteria.getParticipantId())) {
            where.and(eq(ParticipantPrimaryKey.PARTICIPANT_ID_COL, queryCriteria.getParticipantId()));
        }

        return cassandraTemplate.select(select, ParticipantDetails.class);
    }
}
