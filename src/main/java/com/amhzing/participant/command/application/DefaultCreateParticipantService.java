package com.amhzing.participant.command.application;

import com.amhzing.participant.api.command.CreateParticipantCommand;
import com.amhzing.participant.command.domain.gateway.MetaDataEnrichedCommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.concurrent.TimeoutException;

import static org.apache.commons.lang3.Validate.notNull;

public class DefaultCreateParticipantService implements CreateParticipantService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCreateParticipantService.class);

    @Autowired
    private MetaDataEnrichedCommandGateway commandGateway;

    @Override
    public CreatedParticipant create(final UUID participantId,
                                     final UUID correlationId,
                                     final ParticipantToCreate participantToCreate) throws TimeoutException, InterruptedException {
        notNull(participantId);
        notNull(correlationId);
        notNull(participantToCreate);

        final CreateParticipantCommand command = CreateParticipantCommand.create(participantId,
                                                                                 participantToCreate.getName(),
                                                                                 participantToCreate.getAddress(),
                                                                                 participantToCreate.getContactNumber(),
                                                                                 participantToCreate.getEmail());

        final String userId = participantToCreate.getUser().getUserId();

        LOGGER.info("Sending command [{}] with id [{}], correlationId [{}], user [{}]",
                    command.getClass().getSimpleName(),
                    command.getId(),
                    correlationId,
                    userId);

        final Object response = commandGateway.sendAndWait(command, correlationId.toString(), userId);

        return CreatedParticipant.create(participantId(response));
    }

    private String participantId(final Object response) {
        return (response != null) ? response.toString() : "";
    }
}
