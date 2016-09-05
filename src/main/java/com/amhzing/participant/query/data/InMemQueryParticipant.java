package com.amhzing.participant.query.data;

import com.amhzing.participant.query.data.jpa.mapping.ParticipantDetails;
import com.amhzing.participant.query.data.jpa.repository.ParticipantRepository;

import java.util.List;
import java.util.stream.Collectors;

public class InMemQueryParticipant implements QueryParticipant {

    private final ParticipantRepository repository;

    public InMemQueryParticipant(final ParticipantRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<QueryResponse> participantDetails(final QueryCriteria queryCriteria) {

        final List<ParticipantDetails> participants = repository.findByAddress_CountryAllIgnoreCase(queryCriteria.getCountry());

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
                                         .createQueryResponse();
    }
}
