package com.amhzing.participant.web.controller;

import com.amhzing.participant.api.model.*;
import com.amhzing.participant.query.data.ParticipantId;
import com.amhzing.participant.query.data.QueryCriteria;
import com.amhzing.participant.query.data.QueryParticipant;
import com.amhzing.participant.query.data.QueryResponse;
import com.amhzing.participant.web.response.QueryParticipantResponse;
import com.amhzing.participant.web.response.ResponseError;
import com.fasterxml.uuid.Generators;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.amhzing.participant.web.MediaType.APPLICATION_JSON_V1;
import static com.amhzing.participant.web.response.ResponseErrorCode.CANNOT_QUERY_PARTICIPANT;

@RestController
@RequestMapping(path = "/query", method = RequestMethod.GET, produces = APPLICATION_JSON_V1)
public class ParticipantQueryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantQueryController.class);

    @Autowired
    QueryParticipant query;

    @ApiImplicitParams({@ApiImplicitParam(name = "pathVars", dataType = "string", paramType = "path", defaultValue = "IGNORE"),
                        @ApiImplicitParam(name = "country", dataType = "string", paramType = "path"),
                        @ApiImplicitParam(name = "city", dataType = "string", paramType = "path"),
                        @ApiImplicitParam(name = "addressLine1", dataType = "string", paramType = "path"),
                        @ApiImplicitParam(name = "lastName", dataType = "string", paramType = "path"),
                        @ApiImplicitParam(name = "participantId", dataType = "string", paramType = "path")})
    @RequestMapping(path = {"/{country}",
                            "/{country}/{city}",
                            "/{country}/{city}/{addressLine1}",
                            "/{country}/{city}/{addressLine1}/{lastName}",
                            "/{country}/{city}/{addressLine1}/{lastName}/{participantId}"})
    public @Valid QueryParticipantResponse queryByAtLeastCountry(@PathVariable final Map<String, String> pathVars) {

        List<ParticipantInfo> participants = Collections.emptyList();
        try {
            final List<QueryResponse> queryResponse = query.findByCriteria(queryCriteria(pathVars));

            participants = collectParticipants(queryResponse);

            return QueryParticipantResponse.create(participants, ResponseError.empty());
        } catch (final Exception ex) {
            return handleException(participants, ex);
        }
    }

    @RequestMapping("/participantIds")
    public @Valid QueryParticipantResponse queryByParticipantIds(@RequestParam("id") final List<String> participantIds) {

        List<ParticipantInfo> participants = Collections.emptyList();
        try {
            final List<QueryResponse> queryResponse = query.findByIds(collectParticipantIds(participantIds));

            participants = collectParticipants(queryResponse);

            return QueryParticipantResponse.create(participants, ResponseError.empty());
        } catch (final Exception ex) {
            return handleException(participants, ex);
        }
    }

    private QueryParticipantResponse handleException(final List<ParticipantInfo> participants, final Exception ex) {
        final UUID correlationId = Generators.timeBasedGenerator().generate();

        LOGGER.error(CANNOT_QUERY_PARTICIPANT.toString() + " with correlationId: " + correlationId.toString(), ex);
        return QueryParticipantResponse.create(participants,
                                               ResponseError.create(CANNOT_QUERY_PARTICIPANT, correlationId.toString()));
    }

    private List<ParticipantInfo> collectParticipants(final List<QueryResponse> queryResponse) {
        return queryResponse.stream()
                             .map(participant -> ParticipantInfo.create(participantId(participant),
                                                                        name(participant),
                                                                        address(participant),
                                                                        contactNumber(participant),
                                                                        email(participant)))
                             .collect(Collectors.toList());
    }

    private List<ParticipantId> collectParticipantIds(List<String> participantIds) {
        return participantIds.stream()
                             .map(id -> ParticipantId.create(id))
                             .collect(Collectors.toList());
    }

    private QueryCriteria queryCriteria(final @PathVariable Map<String, String> pathVars) {
        return QueryCriteria.create(pathVariable(pathVars, "country"),
                                    pathVariable(pathVars, "city"),
                                    pathVariable(pathVars, "addressLine1"),
                                    pathVariable(pathVars, "lastName"),
                                    pathVariable(pathVars, "participantId"));
    }

    private String pathVariable(final Map<String, String> pathVars, final String pathVar) {
        return MapUtils.getString(pathVars, pathVar, "");
    }

    private Name name(final QueryResponse queryResponse) {
        return Name.create(queryResponse.getFirstName(),
                           queryResponse.getMiddleName(),
                           queryResponse.getLastName(),
                           queryResponse.getSuffix());
    }

    private Address address(final QueryResponse queryResponse) {
        return Address.create(queryResponse.getAddressLine1(),
                              queryResponse.getAddressLine2(),
                              queryResponse.getCity(),
                              queryResponse.getPostalCode(),
                              Country.create(queryResponse.getCountry(), ""));
    }

    private ContactNumber contactNumber(final QueryResponse queryResponse) {
        return ContactNumber.create(queryResponse.getContactNumber());
    }

    private Email email(final QueryResponse queryResponse) {
        return Email.create(queryResponse.getEmail());
    }

    private String participantId(final QueryResponse queryResponse) {
        return queryResponse.getParticipantId();
    }
}
