package com.amhzing.participant.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.amhzing.participant.web.MediaType.APPLICATION_JSON_V1;

@RequestMapping(consumes = APPLICATION_JSON_V1, produces = APPLICATION_JSON_V1)
public class AbstractController {
}
