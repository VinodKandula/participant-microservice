package com.amhzing.participant.web;

import com.amhzing.participant.api.command.CreateParticipantCommand;
import com.amhzing.participant.api.request.CreateParticipantRequest;
import com.amhzing.participant.api.response.CreateParticipantResponse;
import com.amhzing.participant.api.response.ResponseError;
import com.amhzing.participant.gateway.MetaDataEnrichedCommandGateway;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static com.amhzing.participant.api.response.ResponseErrorCode.CANNOT_CREATE_PARTICIPANT;

@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class ParticipantController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantController.class);

    @Autowired
    private MetaDataEnrichedCommandGateway commandGateway;

    // This is thread-safe
    private final TimeBasedGenerator timeBasedGenerator = Generators.timeBasedGenerator();

    @RequestMapping(path = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @Valid CreateParticipantResponse create(@RequestBody @Valid final CreateParticipantRequest request) {

        try {
            final UUID uuid = timeBasedGenerator.generate();

            final CreateParticipantCommand command = CreateParticipantCommand.create(uuid,
                                                                                     request.getName(),
                                                                                     request.getAddress(),
                                                                                     request.getContactNumber(),
                                                                                     request.getEmail());
            final String correlationId = timeBasedGenerator.generate().toString();
            final String userId = request.getUser().getUserId();

            LOGGER.info("Sending command [{}] with id [{}], correlationId [{}], user [{}]",
                        command.getClass().getSimpleName(),
                        command.getId(),
                        correlationId,
                        userId);

            commandGateway.send(command, correlationId, userId);

            return CreateParticipantResponse.create(command.getId().toString(), ResponseError.empty());
        } catch (final Exception ex) {
            LOGGER.error(CANNOT_CREATE_PARTICIPANT.toString(), ex);
            return CreateParticipantResponse.create("", ResponseError.create(CANNOT_CREATE_PARTICIPANT));
        }
    }

}
