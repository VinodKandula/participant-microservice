package com.amhzing.participant.web.controller;

import com.amhzing.participant.query.data.*;
import com.amhzing.participant.web.response.ResponseErrorCode;
import com.fasterxml.uuid.Generators;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Map;
import java.util.UUID;

import static com.amhzing.participant.web.MediaType.APPLICATION_JSON_V1;
import static com.amhzing.participant.web.response.ResponseErrorCode.CANNOT_QUERY_PARTICIPANT;
import static com.amhzing.participant.web.response.ResponseErrorCode.INVALID_REQUEST_CODE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ParticipantQueryController.class)
public class ParticipantQueryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private QueryParticipant queryParticipant;

    private static final String COUNTRY = "SE";
    private UUID participantId = Generators.timeBasedGenerator().generate();

    @Test
    public void should_query_by_country() throws Exception {
        given(queryParticipant.findByCriteria(Matchers.any(QueryCriteria.class))).willReturn(ImmutableList.of(queryResponse()));

        final ResultActions result = this.mvc.perform(get("/query/" + COUNTRY).accept(APPLICATION_JSON_V1))
                                             .andExpect(status().isOk())
                                             .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_V1));

        final String content = result.andReturn().getResponse().getContentAsString();
        final Object document = Configuration.defaultConfiguration().jsonProvider().parse(content);

        assertParticipant(document);
        assertEmptyError(document);
    }

    @Test
    public void should_query_by_participantId() throws Exception {
        given(queryParticipant.findByIds(setOfParticipantIds())).willReturn(ImmutableList.of(queryResponse()));

        final ResultActions result = this.mvc.perform(get("/query/participantIds")
                                                              .accept(APPLICATION_JSON_V1)
                                                              .param("id", participantId.toString()))
                                             .andExpect(status().isOk())
                                             .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_V1));

        final String content = result.andReturn().getResponse().getContentAsString();
        final Object document = Configuration.defaultConfiguration().jsonProvider().parse(content);

        assertParticipant(document);
        assertEmptyError(document);
    }

    @Test
    public void should_create_validation_error_for_missing_id() throws Exception {
        given(queryParticipant.findByIds(setOfParticipantIds())).willReturn(ImmutableList.of(queryResponse()));

        final ResultActions result = this.mvc.perform(get("/query/participantIds")
                                                              .accept(APPLICATION_JSON_V1))
                                             .andExpect(status().isOk())
                                             .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_V1));

        final String content = result.andReturn().getResponse().getContentAsString();
        final Object document = Configuration.defaultConfiguration().jsonProvider().parse(content);

        assertEmptyParticipants(document);
        assertIdMayNotBeEmpty(document);
    }

    @Test
    public void should_fail_query() throws Exception {
        given(queryParticipant.findByIds(setOfParticipantIds())).willThrow(RuntimeException.class);

        final ResultActions result = this.mvc.perform(get("/query/participantIds")
                                                              .accept(APPLICATION_JSON_V1)
                                                              .param("id", participantId.toString()))
                                             .andExpect(status().isOk())
                                             .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_V1));

        final String content = result.andReturn().getResponse().getContentAsString();
        final Object document = Configuration.defaultConfiguration().jsonProvider().parse(content);

        assertEmptyParticipants(document);
        assertErrorWithCorrelationId(document, CANNOT_QUERY_PARTICIPANT);
    }

    private ImmutableSet<ParticipantId> setOfParticipantIds() {
        return ImmutableSet.of(ParticipantId.create(participantId));
    }

    private void assertParticipant(final Object document) {
        final JSONArray participants = JsonPath.read(document, "@.participants");
        assertEquals(participants.size(), 1);

        final String participantId = JsonPath.read(document, "@.participants[0].participantId");
        assertThat(participantId, equalToIgnoringCase(queryResponse().getParticipantId()));

        final String firstName = JsonPath.read(document, "@.participants[0].name.firstName");
        assertThat(firstName, equalToIgnoringCase(queryResponse().getFirstName()));

        final String lastName = JsonPath.read(document, "@.participants[0].name.lastName");
        assertThat(lastName, equalToIgnoringCase(queryResponse().getLastName()));

        final String addressLine1 = JsonPath.read(document, "@.participants[0].address.addressLine1");
        assertThat(addressLine1, equalToIgnoringCase(queryResponse().getAddressLine1()));

        final String city = JsonPath.read(document, "@.participants[0].address.city");
        assertThat(city, equalToIgnoringCase(queryResponse().getCity()));

        final String country = JsonPath.read(document, "@.participants[0].address.country.code");
        assertThat(country, equalToIgnoringCase(queryResponse().getCountry()));
    }

    private void assertEmptyParticipants(final Object document) {
        final JSONArray participants = JsonPath.read(document, "@.participants");
        assertThat(participants, hasSize(0));
    }

    private void assertEmptyError(final Object document) {
        final JSONArray errors = JsonPath.read(document, "@.errors");
        assertThat(errors, hasSize(1));

        final Map<String, String> error = (Map<String, String>) errors.get(0);
        assertThat(error, hasEntry("code", ""));
        assertThat(error, hasEntry("message", ""));
        assertThat(error, hasEntry("correlationId", ""));
    }

    private void assertIdMayNotBeEmpty(final Object document) {
        final JSONArray errors = JsonPath.read(document, "@.errors");
        assertThat(errors, hasSize(1));

        final Map<String, String> error = (Map<String, String>) errors.get(0);
        assertThat(error, hasEntry("code", INVALID_REQUEST_CODE));
        assertThat(error, hasEntry("message", "id:may not be empty"));
        assertThat(error, hasEntry("correlationId", ""));
    }

    private void assertErrorWithCorrelationId(final Object document, final ResponseErrorCode responseErrorCode) {
        final JSONArray errors = JsonPath.read(document, "@.errors");
        assertEquals(errors.size(), 1);

        final Map<String, String> error = (Map<String, String>) errors.get(0);
        assertThat(error, hasEntry("code", responseErrorCode.getCode()));
        assertThat(error, hasEntry("message", responseErrorCode.getMessage()));
        assertThat(error, hasEntry(equalTo("correlationId"), not(isEmptyOrNullString())));
    }

    private QueryResponse queryResponse() {
        return new QueryResponseBuilder().setParticipantId(participantId.toString())
                .setFirstName("John")
                .setLastName("Doe")
                .setAddressLine1("1 Elm Street")
                .setCity("Nashville")
                .setCountry(COUNTRY)
                .create();
    }
}