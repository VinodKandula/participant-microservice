package com.amhzing.participant.web.controller;

import com.amhzing.participant.gateway.MetaDataEnrichedCommandGateway;
import com.amhzing.participant.helper.JsonLoader;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Map;

import static com.amhzing.participant.web.MediaType.APPLICATION_JSON_V1;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ParticipantCommandController.class)
public class ParticipantCommandControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ResourceLoader resourceLoader;

    @MockBean
    private MetaDataEnrichedCommandGateway commandGateway;

    private JsonLoader jsonLoader;

    @Before
    public void setup() throws Exception {
        jsonLoader = new JsonLoader();
    }

    @Test
    public void should_create_participant() throws Exception {
        final Resource resource = resourceLoader.getResource("classpath:create-participant-request.json");
        final String jsonContent = jsonLoader.getJson(resource);

        final ResultActions result = this.mvc.perform((post("/create").contentType(APPLICATION_JSON_V1)
                                                                      .accept(APPLICATION_JSON_V1)
                                                                      .content(jsonContent)))
                                             .andExpect(status().isOk());

        final String content = result.andReturn().getResponse().getContentAsString();
        final Object document = Configuration.defaultConfiguration().jsonProvider().parse(content);

        assertParticipantId(document);

        assertError(document);
    }

    @Test
    public void should_create_error() throws Exception {
        final Resource resource = resourceLoader.getResource("classpath:create-participant-request-error.json");
        final String jsonContent = jsonLoader.getJson(resource);

        final ResultActions result = this.mvc.perform((post("/create").contentType(APPLICATION_JSON_V1)
                                                                      .accept(APPLICATION_JSON_V1)
                                                                      .content(jsonContent)))
                                             .andExpect(status().isOk());

        final String content = result.andReturn().getResponse().getContentAsString();
        final Object document = Configuration.defaultConfiguration().jsonProvider().parse(content);

        assertEmptyParticipantId(document);

        assertNotEmptyError(document);
    }

    private void assertNotEmptyError(final Object document) {
        final JSONArray errors = JsonPath.read(document, "@.errors");
        assertEquals(errors.size(), 1);

        final Map<String, String> error = (Map<String, String>) errors.get(0);
        assertThat(error, hasEntry("code", "G0001"));
        assertThat(error, hasEntry("message", "name.lastName:Invalid length"));
        assertThat(error, hasEntry("correlationId", ""));
    }

    private void assertEmptyParticipantId(final Object document) {
        final String participantId = JsonPath.read(document, "@.participantId");
        assertThat(participantId, isEmptyOrNullString());
    }

    private void assertParticipantId(final Object document) {
        final String participantId = JsonPath.read(document, "@.participantId");
        assertThat(participantId, not(isEmptyOrNullString()));
    }

    private void assertError(final Object document) {
        final JSONArray errors = JsonPath.read(document, "@.errors");
        assertEquals(errors.size(), 1);

        final Map<String, String> error = (Map<String, String>) errors.get(0);
        assertThat(error, hasEntry("code", ""));
        assertThat(error, hasEntry("message", ""));
        assertThat(error, hasEntry("correlationId", ""));
    }
}