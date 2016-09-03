package com.amhzing.participant.command.application;

import java.util.concurrent.TimeoutException;

public interface CreateParticipantService {

    CreatedParticipant create(final ParticipantToCreate participantToCreate) throws TimeoutException, InterruptedException;
}