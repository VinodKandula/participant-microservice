package com.amhzing.participant.web.controller;

import com.amhzing.participant.annotation.LogExecutionTime;
import com.amhzing.participant.api.model.*;
import com.amhzing.participant.query.data.ParticipantId;
import com.amhzing.participant.query.data.QueryCriteria;
import com.amhzing.participant.query.data.QueryParticipant;
import com.amhzing.participant.query.data.QueryResponse;
import com.amhzing.participant.web.request.ParticipantIds;
import com.amhzing.participant.web.response.QueryParticipantResponse;
import com.amhzing.participant.web.response.ResponseError;
import com.fasterxml.uuid.Generators;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static com.amhzing.participant.web.MediaType.APPLICATION_JSON_V1;
import static com.amhzing.participant.web.response.ResponseErrorCode.CANNOT_QUERY_PARTICIPANT;
import static com.amhzing.participant.web.response.ResponseErrorCode.INVALID_REQUEST_CODE;
import static com.google.common.collect.ImmutableList.of;

@RestController
@RequestMapping(path = "/query", method = RequestMethod.GET, produces = APPLICATION_JSON_V1)
public class ParticipantQueryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantQueryController.class);

    private final QueryParticipant query;

    @Autowired
    public ParticipantQueryController(final QueryParticipant query) {
        this.query = query;
    }

    @LogExecutionTime
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

            return QueryParticipantResponse.create(participants, of(ResponseError.empty()));
        } catch (final Exception ex) {
            return handleException(participants, ex);
        }
    }

    @LogExecutionTime
    @RequestMapping("/participantIds")
    public @Valid QueryParticipantResponse queryByParticipantIds(@Valid final ParticipantIds participantIds,
                                                                 final BindingResult bindingResult) {
        List<ParticipantInfo> participants = Collections.emptyList();

        if (bindingResult.hasErrors()) {
            final List<ResponseError> errors = bindingResult.getFieldErrors()
                                                            .stream()
                                                            .map(fieldError -> createResponseError(fieldError))
                                                            .collect(Collectors.toList());

            return QueryParticipantResponse.create(participants, errors);
        }

        try {
            final List<QueryResponse> queryResponse = query.findByIds(collectParticipantIds(participantIds.getId()));

            participants = collectParticipants(queryResponse);

            return QueryParticipantResponse.create(participants, of(ResponseError.empty()));
        } catch (final IllegalArgumentException ex) {
            return QueryParticipantResponse.create(participants,
                                                   of(ResponseError.create(INVALID_REQUEST_CODE,
                                                                           "Not all ids in the request were valid",
                                                                           "")));
        } catch (final Exception ex) {
            return handleException(participants, ex);
        }
    }

    private ResponseError createResponseError(final FieldError fieldError) {
        return ResponseError.create(INVALID_REQUEST_CODE,
                                    fieldError.getField() + ":" + fieldError.getDefaultMessage(),
                                    "");
    }

    private QueryParticipantResponse handleException(final List<ParticipantInfo> participants, final Exception ex) {
        final UUID correlationId = Generators.timeBasedGenerator().generate();

        LOGGER.error(CANNOT_QUERY_PARTICIPANT.toString() + " with correlationId: " + correlationId.toString(), ex);
        return QueryParticipantResponse.create(participants,
                                               of(ResponseError.create(CANNOT_QUERY_PARTICIPANT, correlationId.toString())));
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

    private Set<ParticipantId> collectParticipantIds(final Set<String> participantIds) {
        return participantIds.stream()
                             .map(id -> ParticipantId.create(UUID.fromString(id)))
                             .collect(Collectors.toSet());
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
