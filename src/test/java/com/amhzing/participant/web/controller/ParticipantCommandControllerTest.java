package com.amhzing.participant.web.controller;

import com.amhzing.participant.annotation.TestOffline;
import com.amhzing.participant.command.application.CreateParticipantService;
import com.amhzing.participant.command.application.CreatedParticipant;
import com.amhzing.participant.command.application.ParticipantToCreate;
import com.amhzing.participant.helper.JsonLoader;
import com.amhzing.participant.query.exception.QueryInsertException;
import com.amhzing.participant.web.response.ResponseErrorCode;
import com.fasterxml.uuid.Generators;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Map;
import java.util.UUID;

import static com.amhzing.participant.web.MediaType.APPLICATION_JSON_V1;
import static com.amhzing.participant.web.response.ResponseErrorCode.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ParticipantCommandController.class)
@TestOffline
public class ParticipantCommandControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ResourceLoader resourceLoader;

    @MockBean
    private CreateParticipantService createParticipantService;

    private JsonLoader jsonLoader = new JsonLoader();
    private UUID participantId = Generators.timeBasedGenerator().generate();

    @Test
    public void should_create_participant() throws Exception {
        final Resource resource = resourceLoader.getResource("classpath:create-participant-request.json");
        final String jsonContent = jsonLoader.getJson(resource);

        given(createParticipantService.create(Matchers.any(),
                                              Matchers.any(),
                                              Matchers.any(ParticipantToCreate.class))).willReturn(createdParticipant());

        final ResultActions result = this.mvc.perform(post("/create").contentType(APPLICATION_JSON_V1)
                                                                     .accept(APPLICATION_JSON_V1)
                                                                     .content(jsonContent))
                                             .andExpect(status().isOk())
                                             .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_V1));

        final String content = result.andReturn().getResponse().getContentAsString();
        final Object document = Configuration.defaultConfiguration().jsonProvider().parse(content);

        assertParticipantId(document);

        assertEmptyError(document);
    }

    @Test
    public void should_create_validation_error() throws Exception {
        final Resource resource = resourceLoader.getResource("classpath:create-participant-request-error.json");
        final String jsonContent = jsonLoader.getJson(resource);

        final ResultActions result = this.mvc.perform(post("/create").contentType(APPLICATION_JSON_V1)
                                                                     .accept(APPLICATION_JSON_V1)
                                                                     .content(jsonContent))
                                             .andExpect(status().isOk());

        final String content = result.andReturn().getResponse().getContentAsString();
        final Object document = Configuration.defaultConfiguration().jsonProvider().parse(content);

        assertEmptyParticipantId(document);

        assertNotEmptyError(document);
    }

    @Test
    public void should_create_participant_and_fail_insert() throws Exception {
        final Resource resource = resourceLoader.getResource("classpath:create-participant-request.json");
        final String jsonContent = jsonLoader.getJson(resource);

        given(createParticipantService.create(Matchers.any(),
                                              Matchers.any(),
                                              Matchers.any(ParticipantToCreate.class))).willThrow(QueryInsertException.class);

        final ResultActions result = this.mvc.perform(post("/create").contentType(APPLICATION_JSON_V1)
                                                                     .accept(APPLICATION_JSON_V1)
                                                                     .content(jsonContent))
                                             .andExpect(status().isOk());

        final String content = result.andReturn().getResponse().getContentAsString();
        final Object document = Configuration.defaultConfiguration().jsonProvider().parse(content);

        assertNotEmptyParticipantId(document);

        assertErrorWithCorrelationId(document, CANNOT_INSERT_PARTICIPANT);
    }

    @Test
    public void should_fail_creating_participant() throws Exception {
        final Resource resource = resourceLoader.getResource("classpath:create-participant-request.json");
        final String jsonContent = jsonLoader.getJson(resource);

        given(createParticipantService.create(Matchers.any(),
                                              Matchers.any(),
                                              Matchers.any(ParticipantToCreate.class))).willThrow(RuntimeException.class);

        final ResultActions result = this.mvc.perform(post("/create").contentType(APPLICATION_JSON_V1)
                                                                     .accept(APPLICATION_JSON_V1)
                                                                     .content(jsonContent))
                                             .andExpect(status().isOk());

        final String content = result.andReturn().getResponse().getContentAsString();
        final Object document = Configuration.defaultConfiguration().jsonProvider().parse(content);

        assertEmptyParticipantId(document);

        assertErrorWithCorrelationId(document, CANNOT_CREATE_PARTICIPANT);
    }

    private void assertErrorWithCorrelationId(final Object document, final ResponseErrorCode responseErrorCode) {
        final JSONArray errors = JsonPath.read(document, "@.errors");
        assertEquals(errors.size(), 1);

        final Map<String, String> error = (Map<String, String>) errors.get(0);
        assertThat(error, hasEntry("code", responseErrorCode.getCode()));
        assertThat(error, hasEntry("message", responseErrorCode.getMessage()));
        assertThat(error, hasEntry(equalTo("correlationId"), not(isEmptyOrNullString())));
    }

    private void assertNotEmptyParticipantId(final Object document) {
        final String participantId = JsonPath.read(document, "@.participantId");
        assertThat(participantId, not(isEmptyOrNullString()));
    }

    private void assertParticipantId(final Object document) {
        final String participantId = JsonPath.read(document, "@.participantId");
        assertThat(participantId, not(isEmptyOrNullString()));
        assertEquals(this.participantId.toString(), participantId.toString());
    }

    private void assertEmptyParticipantId(final Object document) {
        final String participantId = JsonPath.read(document, "@.participantId");
        assertThat(participantId, isEmptyOrNullString());
    }

    private void assertEmptyError(final Object document) {
        final JSONArray errors = JsonPath.read(document, "@.errors");
        assertEquals(errors.size(), 1);

        final Map<String, String> error = (Map<String, String>) errors.get(0);
        assertThat(error, hasEntry("code", ""));
        assertThat(error, hasEntry("message", ""));
        assertThat(error, hasEntry("correlationId", ""));
    }

    private void assertNotEmptyError(final Object document) {
        final JSONArray errors = JsonPath.read(document, "@.errors");
        assertEquals(errors.size(), 1);

        final Map<String, String> error = (Map<String, String>) errors.get(0);
        assertThat(error, hasEntry("code", INVALID_REQUEST_CODE));
        assertThat(error, hasEntry("message", "name.lastName:Invalid length"));
        assertThat(error, hasEntry("correlationId", ""));
    }

    private CreatedParticipant createdParticipant() {
        return CreatedParticipant.create(participantId.toString());
    }
}