package com.amhzing.participant.command.application;

import com.amhzing.participant.api.command.CreateParticipantCommand;
import com.amhzing.participant.command.domain.gateway.MetaDataEnrichedCommandGateway;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import org.apache.commons.lang3.StringUtils;
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
    public CreatedParticipant create(final ParticipantToCreate participantToCreate) throws TimeoutException, InterruptedException {

        final TimeBasedGenerator timeBasedGenerator = Generators.timeBasedGenerator();

        final String correlationId = notNull(timeBasedGenerator.generate()).toString();

        final UUID uuid = timeBasedGenerator.generate();
        final CreateParticipantCommand command = CreateParticipantCommand.create(uuid,
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

        commandGateway.send(command, correlationId, userId);

        return CreatedParticipant.create(participantId(command), correlationId);
    }

    private String participantId(final CreateParticipantCommand command) {
        return StringUtils.defaultString(command.getId().toString(), "");
    }
}
