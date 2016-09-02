package com.amhzing.participant.web.controller;

import com.amhzing.participant.application.command.CreatedParticipant;
import com.amhzing.participant.application.command.ParticipantToCreate;
import com.amhzing.participant.application.query.exception.QueryInsertException;
import com.amhzing.participant.web.request.CreateParticipantRequest;
import com.amhzing.participant.web.response.CreateParticipantResponse;
import com.amhzing.participant.web.response.ResponseError;
import com.amhzing.participant.application.command.CreateParticipantService;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.amhzing.participant.web.response.ResponseErrorCode.CANNOT_CREATE_PARTICIPANT;
import static com.amhzing.participant.web.response.ResponseErrorCode.CANNOT_INSERT_PARTICIPANT;
import static com.amhzing.participant.web.response.ResponseErrorCode.INVALID_REQUEST_CODE;
import static com.amhzing.participant.web.MediaType.APPLICATION_JSON_V1;

@RestController
public class ParticipantCommandController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantCommandController.class);

    @Autowired
    private CreateParticipantService createParticipantService;

    @RequestMapping(path = "/create",
                    method = RequestMethod.POST,
                    consumes = APPLICATION_JSON_V1)
    public @Valid CreateParticipantResponse create(@RequestBody @Valid final CreateParticipantRequest request,
                                                   final BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            final List<ResponseError> errors = bindingResult.getFieldErrors()
                                                            .stream()
                                                            .map(fieldError -> createResponseError(fieldError))
                                                            .collect(Collectors.toList());

            return CreateParticipantResponse.create("", errors);
        }

        CreatedParticipant createdParticipant = CreatedParticipant.empty();
        try {
            createdParticipant = createParticipantService.create(ParticipantToCreate.create(request.getName(),
                                                                                       request.getAddress(),
                                                                                       request.getContactNumber(),
                                                                                       request.getEmail(),
                                                                                       request.getUser()));

            return CreateParticipantResponse.create(participantId(createdParticipant),
                                                    ImmutableList.of(ResponseError.empty()));
        } catch (final QueryInsertException insertEx) {
            LOGGER.error(CANNOT_INSERT_PARTICIPANT.toString(), insertEx);
            return CreateParticipantResponse.create(participantId(createdParticipant),
                                                    ImmutableList.of(ResponseError.create(CANNOT_INSERT_PARTICIPANT,
                                                                                          correlationId(createdParticipant))));
        } catch (final Exception ex) {
            LOGGER.error(CANNOT_CREATE_PARTICIPANT.toString(), ex);
            return CreateParticipantResponse.create(participantId(createdParticipant),
                                                    ImmutableList.of(ResponseError.create(CANNOT_CREATE_PARTICIPANT,
                                                                                          correlationId(createdParticipant))));
        }
    }

    private ResponseError createResponseError(final FieldError fieldError) {
        return ResponseError.create(INVALID_REQUEST_CODE,
                                    fieldError.getField() + ":" + fieldError.getDefaultMessage(),
                                    "");
    }

    private String participantId(final CreatedParticipant createdParticipant) {
        return StringUtils.defaultString(createdParticipant.getParticipantId(), "");
    }

    private String correlationId(final CreatedParticipant createdParticipant) {
        return StringUtils.defaultString(createdParticipant.getCorrelationId(), "");
    }
}
