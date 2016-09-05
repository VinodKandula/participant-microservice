package com.amhzing.participant.web.controller;

import com.amhzing.participant.api.model.*;
import com.amhzing.participant.query.data.QueryCriteria;
import com.amhzing.participant.query.data.QueryParticipant;
import com.amhzing.participant.query.data.QueryResponse;
import com.amhzing.participant.web.response.QueryParticipantResponse;
import com.amhzing.participant.web.response.ResponseError;
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
    QueryParticipant query;

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
            final List<QueryResponse> queryResponses = query.participantDetails(queryCriteria(pathVars));

            participants = queryResponses.stream()
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
