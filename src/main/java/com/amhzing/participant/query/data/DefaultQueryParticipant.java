package com.amhzing.participant.query.data;

import com.amhzing.participant.query.data.cassandra.mapping.ParticipantDetailsByCountry;
import com.amhzing.participant.query.exception.QueryIdException;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.stratio.cassandra.lucene.search.condition.builder.BooleanConditionBuilder;
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
import java.util.UUID;

import static com.amhzing.participant.query.data.cassandra.mapping.ParticipantDetails.*;
import static com.amhzing.participant.query.data.cassandra.mapping.ResultSets.queryAllAsObservable;
import static com.stratio.cassandra.lucene.search.SearchBuilders.*;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.trim;
import static org.apache.commons.lang3.StringUtils.wrap;
import static org.apache.commons.lang3.Validate.noNullElements;
import static org.apache.commons.lang3.Validate.notNull;

@CacheConfig(cacheNames = "participantsCache")
public class DefaultQueryParticipant implements QueryParticipant {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultQueryParticipant.class);

    private static final String SELECT_BY_PARTICIPANT_ID = "SELECT * FROM participant_details_by_id WHERE participant_id = ?";

    private static final String LUCENE_SEARCH = "SELECT * FROM participant_details_by_id WHERE lucene = ?";

    private static final String WILDCARD = "*";

    private final CassandraTemplate cassandraTemplate;

    public DefaultQueryParticipant(final CassandraTemplate cassandraTemplate) {
        this.cassandraTemplate = cassandraTemplate;
    }

    @Override
    public List<QueryResponse> findByCriteria(final QueryCriteria queryCriteria) {
        notNull(queryCriteria);

        final BooleanConditionBuilder queryCondition = bool().must(match(COUNTRY, trim(queryCriteria.getCountry())));

        if (isNotBlank(queryCriteria.getCity())) {
            queryCondition.must(wildcard(CITY, wrapAround(queryCriteria.getCity())));
        }

        if (isNotBlank(queryCriteria.getAddressLine1())) {
            queryCondition.must(wildcard(ADDRESS_LINE1, wrapAround(queryCriteria.getAddressLine1())));
        }

        if (isNotBlank(queryCriteria.getLastName())) {
            queryCondition.must(wildcard(LAST_NAME, wrapAround(queryCriteria.getLastName())));
        }

        if (isNotBlank(queryCriteria.getParticipantId())) {
            try {
                UUID.fromString(trim(queryCriteria.getParticipantId()));
            } catch (final IllegalArgumentException ex) {
                throw new QueryIdException("Invalid id", ex);
            }

            queryCondition.must(match(PARTICIPANT_ID, queryCriteria.getParticipantId()));
        }

        final ResultSet query = cassandraTemplate.getSession()
                                                 .execute(LUCENE_SEARCH, filter(queryCondition).toJson());

        return query.all()
                    .stream()
                    .map(this::buildQueryResponse)
                    .collect(toList());
    }

    @Override
    @Cacheable(unless = "#result == null")
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
                                         .setContactNumber(row.getString(CONTACT_NUMBER))
                                         .setEmail(row.getString(EMAIL))
                                         .create();
    }

    private String wrapAround(final String text) {
        return wrap(trim(text), WILDCARD);
    }
}
