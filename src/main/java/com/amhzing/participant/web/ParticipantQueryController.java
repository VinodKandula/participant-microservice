package com.amhzing.participant.web;

import com.amhzing.participant.api.model.*;
import com.amhzing.participant.api.request.QueryParticipantRequest;
import com.amhzing.participant.api.response.QueryParticipantResponse;
import com.amhzing.participant.api.response.ResponseError;
import com.amhzing.participant.query.QueryParticipantDetails;
import com.amhzing.participant.query.mapping.ParticipantDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.amhzing.participant.api.response.ResponseErrorCode.CANNOT_QUERY_PARTICIPANT;

@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class ParticipantQueryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantQueryController.class);

    @Autowired
    QueryParticipantDetails query;

    @RequestMapping(path = "/query", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @Valid QueryParticipantResponse create(@RequestBody @Valid final QueryParticipantRequest request) {

        List<ParticipantInfo> participants = Collections.emptyList();
        try {
            final List<ParticipantDetails> participantDetails = query.participantDetails(request.getCountry(),
                                                                                         request.getCity(),
                                                                                         request.getAddressLine1(),
                                                                                         request.getLastName(),
                                                                                         request.getParticipantId());

            participants = participantDetails.stream()
                                             .map(participant -> ParticipantInfo.create(name(participant),
                                                                                        address(participant),
                                                                                        contactNumber(participant),
                                                                                        email(participant)))
                                             .collect(Collectors.toList());

            return QueryParticipantResponse.create(participants, ResponseError.empty());
        } catch (final Exception ex) {
            LOGGER.error(CANNOT_QUERY_PARTICIPANT.toString(), ex);
            return QueryParticipantResponse.create(participants,
                                                    ResponseError.create(CANNOT_QUERY_PARTICIPANT, ""));
        }
    }

    private Name name(final ParticipantDetails participant) {
        return Name.create(participant.getFirstName(),
                                                  participant.getMiddleName(),
                                                  participant.getPrimaryKey().getLastName(),
                                                  participant.getSuffix());
    }

    private Address address(final ParticipantDetails participant) {
        return Address.create(participant.getPrimaryKey().getAddressLine1(),
                              participant.getAddressLine2(),
                              participant.getPrimaryKey().getCity(),
                              participant.getPostalCode(),
                              Country.create(participant.getPrimaryKey().getCountry(), ""));
    }

    private ContactNumber contactNumber(final ParticipantDetails participant) {
        return ContactNumber.create(participant.getContactNumber());
    }

    private Email email(final ParticipantDetails participant) {
        return Email.create(participant.getEmail());
    }
}
