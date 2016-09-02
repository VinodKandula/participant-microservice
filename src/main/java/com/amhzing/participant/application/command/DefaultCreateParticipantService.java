package com.amhzing.participant.application.command;

import com.amhzing.participant.api.command.CreateParticipantCommand;
import com.amhzing.participant.web.response.CreateParticipantResponse;
import com.amhzing.participant.web.response.ResponseError;
import com.amhzing.participant.application.query.exception.QueryInsertException;
import com.amhzing.participant.domain.gateway.MetaDataEnrichedCommandGateway;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static com.amhzing.participant.web.response.ResponseErrorCode.CANNOT_CREATE_PARTICIPANT;
import static com.amhzing.participant.web.response.ResponseErrorCode.CANNOT_INSERT_PARTICIPANT;
import static org.apache.commons.lang3.Validate.notNull;

public class DefaultCreateParticipantService implements CreateParticipantService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCreateParticipantService.class);

    @Autowired
    private MetaDataEnrichedCommandGateway commandGateway;

    @Override
    public CreateParticipantResponse create(final CreateParticipant createParticipant) {

        final TimeBasedGenerator timeBasedGenerator = Generators.timeBasedGenerator();

        CreateParticipantCommand command = CreateParticipantCommand.empty();
        String correlationId = "";

        try {
            correlationId = notNull(timeBasedGenerator.generate()).toString();

            final UUID uuid = timeBasedGenerator.generate();
            command = CreateParticipantCommand.create(uuid,
                                                      createParticipant.getName(),
                                                      createParticipant.getAddress(),
                                                      createParticipant.getContactNumber(),
                                                      createParticipant.getEmail());

            final String userId = createParticipant.getUser().getUserId();

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

    private String participantId(final CreateParticipantCommand command) {
        return StringUtils.defaultString(command.getId().toString(), "");
    }
}
