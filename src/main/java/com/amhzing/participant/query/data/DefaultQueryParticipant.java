package com.amhzing.participant.query.data;

import com.amhzing.participant.query.data.cassandra.mapping.ParticipantDetails;
import com.amhzing.participant.query.data.cassandra.mapping.ParticipantPrimaryKey;
import com.datastax.driver.core.querybuilder.Select;
import org.springframework.data.cassandra.core.CassandraTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;
import static org.apache.commons.lang.StringUtils.isNotBlank;

public class DefaultQueryParticipant implements QueryParticipant {

    private final CassandraTemplate cassandraTemplate;

    public DefaultQueryParticipant(final CassandraTemplate cassandraTemplate) {
        this.cassandraTemplate = cassandraTemplate;
    }

    @Override
    public List<QueryResponse> participantDetails(final QueryCriteria queryCriteria) {

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

        final List<ParticipantDetails> participants = cassandraTemplate.select(select, ParticipantDetails.class);

        return participants.stream()
                           .map(participant -> buildQueryResponse(participant))
                           .collect(Collectors.toList());
    }

    private QueryResponse buildQueryResponse(final ParticipantDetails participantDetails) {
        return new QueryResponseBuilder().setParticipantId(participantDetails.getPrimaryKey().getParticipantId().toString())
                                         .setFirstName(participantDetails.getFirstName())
                                         .setLastName(participantDetails.getPrimaryKey().getLastName())
                                         .setMiddleName(participantDetails.getMiddleName())
                                         .setSuffix(participantDetails.getSuffix())
                                         .setAddressLine1(participantDetails.getPrimaryKey().getAddressLine1())
                                         .setAddressLine2(participantDetails.getAddressLine2())
                                         .setCity(participantDetails.getPrimaryKey().getCity())
                                         .setCountry(participantDetails.getPrimaryKey().getCountry())
                                         .setPostalCode(participantDetails.getPostalCode())
                                         .setContactNumber(participantDetails.getContactNumber())
                                         .setEmail(participantDetails.getEmail())
                                         .createQueryResponse();
    }
}
