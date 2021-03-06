package com.amhzing.participant.web.response;

import com.amhzing.participant.annotation.TestOffline;
import com.amhzing.participant.api.model.ParticipantInfo;
import com.amhzing.participant.helper.JsonLoader;
import com.google.common.collect.ImmutableList;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static com.amhzing.participant.helper.ParticipantApiModelHelper.*;
import static com.amhzing.participant.helper.ParticipantApiModelHelper.address;
import static com.amhzing.participant.web.response.ResponseErrorCode.CANNOT_QUERY_PARTICIPANT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@JsonTest
@TestOffline
public class QueryParticipantResponseTest {

    @Autowired
    private JacksonTester<QueryParticipantResponse> json;

    @Autowired
    private ResourceLoader resourceLoader;

    private JsonLoader jsonLoader;

    @Before
    public void setUp() throws Exception {
        jsonLoader = new JsonLoader();
    }

    @Test
    public void should_serialize_response() throws Exception {
        final QueryParticipantResponse response = QueryParticipantResponse.create(ImmutableList.of(participantInfo()),
                                                                                 ImmutableList.of(responseError()));

        final JsonContent<QueryParticipantResponse> jsonContent = this.json.write(response);

        assertParticipants(jsonContent.getJson());
        assertErrorWithCorrelationId(jsonContent.getJson(), CANNOT_QUERY_PARTICIPANT, correlationId());
    }

    private void assertParticipants(final String document) {
        final JSONArray participants = JsonPath.read(document, "@.participants");
        assertEquals(participants.size(), 1);

        final Map<String, ?> participant = (Map<String, String>) participants.get(0);
        assertThat(participant, hasEntry("participantId", participantId()));
        assertThat((Map<String, String>) participant.get("name"), hasEntry("lastName", name().getLastName()));

        final Map<String, ?> address = (Map<String, String>) participant.get("address");
        assertThat(address, hasEntry("addressLine1", address().getAddressLine1()));
        assertThat((Map<String, String>) address.get("country"), hasEntry("code", address().getCountry().getCode()));

        assertThat((Map<String, String>) participant.get("contactNumber"), hasEntry("primaryNumber", contactNumber().getValue()));
        assertThat((Map<String, String>) participant.get("email"), hasEntry("primaryEmail", email().getValue()));
    }

    private void assertErrorWithCorrelationId(final String document,
                                              final ResponseErrorCode responseErrorCode,
                                              final String correlationId) {
        final JSONArray errors = JsonPath.read(document, "@.errors");
        assertEquals(errors.size(), 1);

        final Map<String, String> error = (Map<String, String>) errors.get(0);
        assertThat(error, hasEntry("code", responseErrorCode.getCode()));
        assertThat(error, hasEntry("message", responseErrorCode.getMessage()));
        assertThat(error, hasEntry("correlationId", correlationId));
    }

    private ParticipantInfo participantInfo() {
        return ParticipantInfo.create(participantId(),
                                      name(),
                                      address(),
                                      contactNumber(),
                                      email());
    }

    private String participantId() {
        return "12345";
    }

    private String correlationId() {
        return "19837";
    }

    private ResponseError responseError() {
        return ResponseError.create(CANNOT_QUERY_PARTICIPANT, correlationId());
    }
}