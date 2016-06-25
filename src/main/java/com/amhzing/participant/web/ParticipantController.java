package com.amhzing.participant.web;

import com.amhzing.participant.api.Name;
import com.amhzing.participant.api.command.CreateParticipantCommand;
import com.fasterxml.uuid.Generators;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ParticipantController {

    @Autowired
    private CommandGateway commandGateway;

    @RequestMapping("/create")
    @Transactional
    @ResponseBody
    public String create(@RequestParam("firstName") String firstName,
                       @RequestParam("middleName") String middleName,
                       @RequestParam("lastName") String lastName,
                       @RequestParam("suffix") String suffix) {

        final UUID uuid = Generators.timeBasedGenerator().generate();
        final Name name = Name.create(firstName, middleName, lastName, suffix);

        final CreateParticipantCommand command = new CreateParticipantCommand(uuid, name);
        commandGateway.send(command);

        return firstName + " " + middleName + " " + lastName + " " + suffix;
    }

}
