package com.amhzing.participant.command.application;

import java.util.UUID;
import java.util.concurrent.TimeoutException;

public interface CreateParticipantService {

    CreatedParticipant create(final UUID participantId,
                              final UUID correlationId,
                              final ParticipantToCreate participantToCreate) throws TimeoutException, InterruptedException;
}
