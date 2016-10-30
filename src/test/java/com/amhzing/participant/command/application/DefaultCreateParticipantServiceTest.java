package com.amhzing.participant.command.application;

import com.amhzing.participant.command.domain.gateway.MetaDataEnrichedCommandGateway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static com.amhzing.participant.helper.ParticipantApiModelHelper.participantToCreate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class DefaultCreateParticipantServiceTest {

    private static final String PARTICIPANT_ID = "12345";

    @Mock
    private MetaDataEnrichedCommandGateway commandGateway;

    private DefaultCreateParticipantService createParticipantService;

    @Before
    public void setUp() {
        createParticipantService = new DefaultCreateParticipantService(commandGateway);
    }

    @Test
    public void should_create_participant() throws Exception {
        given(commandGateway.sendAndWait(any(), any(), any())).willReturn(PARTICIPANT_ID);

        final CreatedParticipant createdParticipant = createParticipantService.create(UUID.randomUUID(),
                                                                                      UUID.randomUUID(),
                                                                                      participantToCreate());

        assertThat(createdParticipant).isNotNull();
        assertThat(createdParticipant.getParticipantId()).isEqualToIgnoringCase(PARTICIPANT_ID);
    }
}