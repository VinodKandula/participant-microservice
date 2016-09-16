package com.amhzing.participant.query.data;

import com.amhzing.participant.query.data.jpa.mapping.ParticipantDetails;
import com.amhzing.participant.query.data.jpa.mapping.QParticipantDetails;
import com.amhzing.participant.query.data.jpa.repository.ParticipantQueryDslRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang.StringUtils.isNotBlank;

@CacheConfig(cacheNames = "participantsCache")
public class InMemQueryParticipant implements QueryParticipant {

    private final ParticipantQueryDslRepository repository;

    public InMemQueryParticipant(final ParticipantQueryDslRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<QueryResponse> findByCriteria(final QueryCriteria queryCriteria) {

        final QParticipantDetails qParticipantDetails = QParticipantDetails.participantDetails;

        BooleanExpression predicate = qParticipantDetails.address.country.equalsIgnoreCase(queryCriteria.getCountry());

        if (isNotBlank(queryCriteria.getCity())) {
            predicate = predicate.and(qParticipantDetails.address.city.equalsIgnoreCase(queryCriteria.getCity()));
        }

        if (isNotBlank(queryCriteria.getAddressLine1())) {
            predicate = predicate.and(qParticipantDetails.address.addressLine1.equalsIgnoreCase(queryCriteria.getAddressLine1()));
        }

        if (isNotBlank(queryCriteria.getLastName())) {
            predicate = predicate.and(qParticipantDetails.name.lastName.equalsIgnoreCase(queryCriteria.getLastName()));
        }

        if (isNotBlank(queryCriteria.getParticipantId())) {
            predicate = predicate.and(qParticipantDetails.participantId.equalsIgnoreCase(queryCriteria.getParticipantId()));
        }

        final List<ParticipantDetails> participants = repository.findAll(predicate);

        return collectQueryResponse(participants);
    }

    @Override
    @Cacheable
    public List<QueryResponse> findByIds(final Set<ParticipantId> participantIds) {
        final Set<String> collectIds = participantIds.stream().map(id -> id.getValue().toString()).collect(Collectors.toSet());
        final List<ParticipantDetails> participants = repository.findByParticipantIdIn(collectIds);

        return collectQueryResponse(participants);
    }

    private List<QueryResponse> collectQueryResponse(final List<ParticipantDetails> participants) {
        return participants.stream()
                           .map(participant -> buildQueryResponse(participant))
                           .collect(Collectors.toList());
    }

    private QueryResponse buildQueryResponse(final ParticipantDetails participantDetails) {
        return new QueryResponseBuilder().setParticipantId(participantDetails.getParticipantId())
                                         .setFirstName(participantDetails.getName().getFirstName())
                                         .setLastName(participantDetails.getName().getLastName())
                                         .setMiddleName(participantDetails.getName().getMiddleName())
                                         .setSuffix(participantDetails.getName().getSuffix())
                                         .setAddressLine1(participantDetails.getAddress().getAddressLine1())
                                         .setAddressLine2(participantDetails.getAddress().getAddressLine2())
                                         .setCity(participantDetails.getAddress().getCity())
                                         .setCountry(participantDetails.getAddress().getCountry())
                                         .setPostalCode(participantDetails.getAddress().getPostalCode())
                                         .setContactNumber(participantDetails.getContactNumber())
                                         .setEmail(participantDetails.getEmail())
                                         .create();
    }
}
