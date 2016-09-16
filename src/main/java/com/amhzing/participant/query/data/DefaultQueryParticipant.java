package com.amhzing.participant.query.data;

import com.amhzing.participant.query.data.cassandra.mapping.ParticipantDetailsByCountry;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.cassandra.core.CassandraTemplate;
import rx.Observer;
import rx.observables.BlockingObservable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.amhzing.participant.query.data.cassandra.mapping.ParticipantDetails.*;
import static com.amhzing.participant.query.data.cassandra.mapping.ParticipantPrimaryKey.*;
import static com.amhzing.participant.query.data.cassandra.mapping.ResultSets.queryAllAsObservable;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.apache.commons.lang.StringUtils.lowerCase;
import static org.apache.commons.lang3.Validate.noNullElements;
import static org.apache.commons.lang3.Validate.notNull;

@CacheConfig(cacheNames = "participantsCache")
public class DefaultQueryParticipant implements QueryParticipant {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultQueryParticipant.class);

    public static final String SELECT_BY_PARTICIPANT_ID = "SELECT * FROM participant_details_by_id WHERE participant_id = ?";

    private final CassandraTemplate cassandraTemplate;

    public DefaultQueryParticipant(final CassandraTemplate cassandraTemplate) {
        this.cassandraTemplate = cassandraTemplate;
    }

    @Override
    public List<QueryResponse> findByCriteria(final QueryCriteria queryCriteria) {
        notNull(queryCriteria);

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

        final ResultSet query = cassandraTemplate.query(select);

        return query.all()
                    .stream()
                    .map(this::buildQueryResponse)
                    .collect(toList());
    }

    @Override
    @Cacheable
    public List<QueryResponse> findByIds(final Set<ParticipantId> participantIds) {
        noNullElements(participantIds);

        final Object[] ids = participantIds.stream().map(id -> id.getValue()).toArray();

        // TODO - Make this non blocking
        final BlockingObservable<ResultSet> results = queryAllAsObservable(cassandraTemplate.getSession(),
                                                                           SELECT_BY_PARTICIPANT_ID,
                                                                           ids).toBlocking();

        final List<QueryResponse> queryResponses = new ArrayList<>();

        results.subscribe(new Observer<ResultSet>() {
            @Override public void onNext(ResultSet resultSet) {
                resultSet.all()
                         .stream()
                         .map(row -> buildQueryResponse(row))
                         .collect(collectingAndThen(toList(), queryResponses::addAll));
            }

            @Override public void onError(Throwable throwable) {
                LOGGER.error("Could not retrieve details by id", throwable);
            }

            @Override public void onCompleted() {
            }
        });

        return queryResponses;
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
                                         .create();
    }

    private QueryResponse buildQueryResponse(final Row row) {
        return new QueryResponseBuilder().setParticipantId(row.getUUID(PARTICIPANT_ID).toString())
                                         .setFirstName(row.getString(FIRST_NAME))
                                         .setMiddleName(row.getString(MIDDLE_NAME))
                                         .setLastName(row.getString(LAST_NAME))
                                         .setSuffix(row.getString(SUFFIX))
                                         .setAddressLine1(row.getString(ADDRESS_LINE1))
                                         .setAddressLine2(row.getString(ADDRESS_LINE2))
                                         .setCity(row.getString(CITY))
                                         .setCountry(row.getString(COUNTRY))
                                         .setPostalCode(row.getString(POSTAL_CODE))
                                         .setContactNumber(row.getString(EMAIL))
                                         .setEmail(row.getString(CONTACT_NUMBER))
                                         .create();
    }
}
