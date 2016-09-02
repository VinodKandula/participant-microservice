package com.amhzing.participant.application.command;

import com.amhzing.participant.web.response.CreateParticipantResponse;

public interface CreateParticipantService {

    CreateParticipantResponse create(final CreateParticipant createParticipant);
}
