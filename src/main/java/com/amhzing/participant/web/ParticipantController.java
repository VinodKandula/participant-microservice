package com.amhzing.participant.web;

import com.amhzing.participant.api.Name;
import com.amhzing.participant.api.command.CreateParticipantCommand;
import com.amhzing.participant.gateway.MetaDataEnrichedCommandGateway;
import com.fasterxml.uuid.Generators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeoutException;

@RestController
public class ParticipantController {

    @Autowired
    private MetaDataEnrichedCommandGateway commandGateway;

    @RequestMapping("/create")
    @Transactional
    @ResponseBody
    public String create(@RequestParam("firstName") String firstName,
                       @RequestParam("middleName") String middleName,
                       @RequestParam("lastName") String lastName,
                       @RequestParam("suffix") String suffix) throws TimeoutException, InterruptedException {

        final UUID uuid = Generators.timeBasedGenerator().generate();
        final Name name = Name.create(firstName, middleName, lastName, suffix);

        final CreateParticipantCommand command = new CreateParticipantCommand(uuid, name);
        commandGateway.sendAndWait(command, "56789", "mahnDT2");

        return firstName + " " + middleName + " " + lastName + " " + suffix;
    }

}
