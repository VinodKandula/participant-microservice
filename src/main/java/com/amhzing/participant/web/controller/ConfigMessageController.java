package com.amhzing.participant.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class ConfigMessageController {

    @Value("${config.message}")
    private String configMessage;

    @RequestMapping(path = "/config-message", method = RequestMethod.GET)
    public String configMessage() {
        return this.configMessage;
    }
}
