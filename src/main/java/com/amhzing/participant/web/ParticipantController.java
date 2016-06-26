package com.amhzing.participant.web;

import com.amhzing.participant.api.Name;
import com.amhzing.participant.api.command.CreateParticipantCommand;
import com.amhzing.participant.gateway.MetaDataEnrichedCommandGateway;
import com.fasterxml.uuid.Generators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping(path = "/participant", consumes = "application/json")
public class ParticipantController {

    @Autowired
    private MetaDataEnrichedCommandGateway commandGateway;

    @RequestMapping(path = "create", method = RequestMethod.POST)
    @Transactional
    public String create(@RequestBody @Valid final Name name) throws TimeoutException, InterruptedException {

        final UUID uuid = Generators.timeBasedGenerator().generate();

        final CreateParticipantCommand command = new CreateParticipantCommand(uuid, name);
        commandGateway.sendAndWait(command, "56789", "mahnDT2");

        return name.toString();
    }

}
