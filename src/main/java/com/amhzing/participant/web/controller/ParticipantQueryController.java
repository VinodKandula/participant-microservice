package com.amhzing.participant.web.controller;

import com.amhzing.participant.api.model.*;
import com.amhzing.participant.web.response.QueryParticipantResponse;
import com.amhzing.participant.web.response.ResponseError;
import com.amhzing.participant.application.query.QueryParticipantDetails;
import com.amhzing.participant.application.query.mapping.ParticipantDetails;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.amhzing.participant.web.response.ResponseErrorCode.CANNOT_QUERY_PARTICIPANT;

@RestController
public class ParticipantQueryController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantQueryController.class);

    @Autowired
    QueryParticipantDetails query;

    @ApiImplicitParams({@ApiImplicitParam(name = "pathVars", dataType = "string", paramType = "path", defaultValue = "IGNORE"),
                        @ApiImplicitParam(name = "country", dataType = "string", paramType = "path"),
                        @ApiImplicitParam(name = "city", dataType = "string", paramType = "path"),
                        @ApiImplicitParam(name = "addressLine1", dataType = "string", paramType = "path"),
                        @ApiImplicitParam(name = "lastName", dataType = "string", paramType = "path"),
                        @ApiImplicitParam(name = "participantId", dataType = "string", paramType = "path")})
    @RequestMapping(path = {"/query/{country}",
                            "/query/{country}/{city}",
                            "/query/{country}/{city}/{addressLine1}",
                            "/query/{country}/{city}/{addressLine1}/{lastName}",
                            "/query/{country}/{city}/{addressLine1}/{lastName}/{participantId}"},
                    method = RequestMethod.GET)
    public @Valid QueryParticipantResponse create(@PathVariable final Map<String, String> pathVars) {

        List<ParticipantInfo> participants = Collections.emptyList();
        try {
            final List<ParticipantDetails> participantDetails = query.participantDetails(pathVariable(pathVars, "country"),
                                                                                         pathVariable(pathVars, "city"),
                                                                                         pathVariable(pathVars, "addressLine1"),
                                                                                         pathVariable(pathVars, "lastName"),
                                                                                         pathVariable(pathVars, "participantId"));

            participants = participantDetails.stream()
                                             .map(participant -> ParticipantInfo.create(participantId(participant),
                                                                                        name(participant),
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

    private String pathVariable(final Map<String, String> pathVars, final String pathVar) {
        return MapUtils.getString(pathVars, pathVar, "");
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

    private String participantId(final ParticipantDetails participant) {
        return participant.getPrimaryKey().getParticipantId().toString();
    }
}
