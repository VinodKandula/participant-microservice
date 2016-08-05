package com.amhzing.participant.web;

import com.amhzing.participant.api.Name;
import com.amhzing.participant.api.ParticipantId;
import com.amhzing.participant.api.command.CreateParticipantCommand;
import com.amhzing.participant.gateway.MetaDataEnrichedCommandGateway;
import com.fasterxml.uuid.Generators;
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
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class ParticipantController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantController.class);

    @Autowired
    private MetaDataEnrichedCommandGateway commandGateway;

    @RequestMapping(path = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @Valid ParticipantId create(@RequestBody @Valid final Name name) throws TimeoutException, InterruptedException {

        final UUID uuid = Generators.timeBasedGenerator().generate();

        final CreateParticipantCommand command = new CreateParticipantCommand(uuid, name);
        final String correlationId = "56789";
        final String userId = "mahnDT2";

        LOGGER.info("Sending command {} with id {}, correlationId {}, user {}", command.getClass(), command.getId(), correlationId, userId);
        commandGateway.send(command, correlationId, userId);

        return ParticipantId.create(command.getId().toString());
    }

}
