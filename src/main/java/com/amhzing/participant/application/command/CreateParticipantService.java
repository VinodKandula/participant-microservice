package com.amhzing.participant.application.command;

import java.util.concurrent.TimeoutException;

public interface CreateParticipantService {

    CreatedParticipant create(final ParticipantToCreate participantToCreate) throws TimeoutException, InterruptedException;
}
