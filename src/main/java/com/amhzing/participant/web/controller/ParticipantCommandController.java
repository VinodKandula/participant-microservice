package com.amhzing.participant.web.controller;

import com.amhzing.participant.api.command.CreateParticipantCommand;
import com.amhzing.participant.api.request.CreateParticipantRequest;
import com.amhzing.participant.api.response.CreateParticipantResponse;
import com.amhzing.participant.api.response.ResponseError;
import com.amhzing.participant.gateway.MetaDataEnrichedCommandGateway;
import com.amhzing.participant.query.exception.QueryInsertException;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
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
import java.util.UUID;
import java.util.stream.Collectors;

import static com.amhzing.participant.api.response.ResponseErrorCode.*;
import static com.amhzing.participant.web.MediaType.APPLICATION_JSON_V1;
import static org.apache.commons.lang3.Validate.notNull;

@RestController
public class ParticipantCommandController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantCommandController.class);

    @Autowired
    private MetaDataEnrichedCommandGateway commandGateway;

    // This is thread-safe
    private final TimeBasedGenerator timeBasedGenerator = Generators.timeBasedGenerator();

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

        CreateParticipantCommand command = CreateParticipantCommand.empty();
        String correlationId = "";

        try {
            correlationId = notNull(timeBasedGenerator.generate()).toString();

            final UUID uuid = timeBasedGenerator.generate();
            command = CreateParticipantCommand.create(uuid,
                                                      request.getName(),
                                                      request.getAddress(),
                                                      request.getContactNumber(),
                                                      request.getEmail());

            final String userId = request.getUser().getUserId();

            LOGGER.info("Sending command [{}] with id [{}], correlationId [{}], user [{}]",
                        command.getClass().getSimpleName(),
                        command.getId(),
                        correlationId,
                        userId);

            commandGateway.send(command, correlationId, userId);

            return CreateParticipantResponse.create(participantId(command), ImmutableList.of(ResponseError.empty()));
        } catch (final QueryInsertException insertEx) {
            LOGGER.error(CANNOT_INSERT_PARTICIPANT.toString(), insertEx);
            return CreateParticipantResponse.create(participantId(command),
                                                    ImmutableList.of(ResponseError.create(CANNOT_INSERT_PARTICIPANT,
                                                                                          correlationId)));
        } catch (final Exception ex) {
            LOGGER.error(CANNOT_CREATE_PARTICIPANT.toString(), ex);
            return CreateParticipantResponse.create(participantId(command),
                                                    ImmutableList.of(ResponseError.create(CANNOT_CREATE_PARTICIPANT,
                                                                                          correlationId)));
        }
    }

    private ResponseError createResponseError(final FieldError fieldError) {
        return ResponseError.create(INVALID_REQUEST_CODE,
                                    fieldError.getField() + ":" + fieldError.getDefaultMessage(),
                                    "");
    }

    private String participantId(final CreateParticipantCommand command) {
        return StringUtils.defaultString(command.getId().toString(), "");
    }

}
