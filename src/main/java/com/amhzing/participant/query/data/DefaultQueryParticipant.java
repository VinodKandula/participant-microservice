package com.amhzing.participant.query.data;

import com.amhzing.participant.query.data.cassandra.mapping.ParticipantDetailsByCountry;
import com.datastax.driver.core.querybuilder.Select;
import org.springframework.data.cassandra.core.CassandraTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static com.amhzing.participant.query.data.cassandra.mapping.ParticipantPrimaryKey.*;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.apache.commons.lang.StringUtils.lowerCase;

public class DefaultQueryParticipant implements QueryParticipant {

    private final CassandraTemplate cassandraTemplate;

    public DefaultQueryParticipant(final CassandraTemplate cassandraTemplate) {
        this.cassandraTemplate = cassandraTemplate;
    }

    @Override
    public List<QueryResponse> findByCriteria(final QueryCriteria queryCriteria) {

        final Select select = select().from("participant_details_by_country");
        final Select.Where where = select.where(eq(COUNTRY_LOWERCASE, lowerCase(queryCriteria.getCountry())));

        if (isNotBlank(queryCriteria.getCity())) {
            where.and(eq(CITY_LOWERCASE, lowerCase(queryCriteria.getCity())));
        }

        if (isNotBlank(queryCriteria.getAddressLine1())) {
            where.and(eq(ADDRESS_LINE1_LOWERCASE, lowerCase(queryCriteria.getAddressLine1())));
        }

        if (isNotBlank(queryCriteria.getLastName())) {
            where.and(eq(LAST_NAME_LOWERCASE, lowerCase(queryCriteria.getLastName())));
        }

        if (isNotBlank(queryCriteria.getParticipantId())) {
            where.and(eq(PARTICIPANT_ID, queryCriteria.getParticipantId()));
        }

        final List<ParticipantDetailsByCountry> participants = cassandraTemplate.select(select, ParticipantDetailsByCountry.class);

        return participants.stream()
                           .map(participant -> buildQueryResponse(participant))
                           .collect(Collectors.toList());
    }

    @Override
    public List<QueryResponse> findByIds(final List<ParticipantId> participantIds) {
        // FIXME - Should implement this
        return null;
    }

    private QueryResponse buildQueryResponse(final ParticipantDetailsByCountry participantDetails) {
        return new QueryResponseBuilder().setParticipantId(participantDetails.getPrimaryKey().getParticipantId().toString())
                                         .setFirstName(participantDetails.getFirstName())
                                         .setLastName(participantDetails.getLastName())
                                         .setMiddleName(participantDetails.getMiddleName())
                                         .setSuffix(participantDetails.getSuffix())
                                         .setAddressLine1(participantDetails.getAddressLine1())
                                         .setAddressLine2(participantDetails.getAddressLine2())
                                         .setCity(participantDetails.getCity())
                                         .setCountry(participantDetails.getCountry())
                                         .setPostalCode(participantDetails.getPostalCode())
                                         .setContactNumber(participantDetails.getContactNumber())
                                         .setEmail(participantDetails.getEmail())
                                         .createQueryResponse();
    }
}
