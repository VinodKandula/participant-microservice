package com.amhzing.participant.web.response;

import com.amhzing.participant.annotation.TestOffline;
import com.amhzing.participant.helper.JsonLoader;
import com.google.common.collect.ImmutableList;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.assertj.core.api.Assertions;
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

import static com.amhzing.participant.web.response.ResponseErrorCode.CANNOT_CREATE_PARTICIPANT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@JsonTest
@TestOffline
public class CreateParticipantResponseTest {

    @Autowired
    private JacksonTester<CreateParticipantResponse> json;

    @Autowired
    private ResourceLoader resourceLoader;

    private JsonLoader jsonLoader;

    @Before
    public void setUp() throws Exception {
        jsonLoader = new JsonLoader();
    }

    @Test
    public void should_serialize_response() throws Exception {
        final CreateParticipantResponse response = CreateParticipantResponse.create(participantId(),
                                                                                   ImmutableList.of(responseError()));

        final JsonContent<CreateParticipantResponse> jsonContent = this.json.write(response);

        assertParticipantId(jsonContent);
        assertErrorWithCorrelationId(jsonContent.getJson(), CANNOT_CREATE_PARTICIPANT, correlationId());
    }

    private void assertParticipantId(final JsonContent<CreateParticipantResponse> jsonContent) {
        Assertions.assertThat(jsonContent).hasJsonPathStringValue("@.participantId");
        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("@.participantId").isEqualTo(participantId());
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

    private String participantId() {
        return "12345";
    }

    private String correlationId() {
        return "19837";
    }

    private ResponseError responseError() {
        return ResponseError.create(CANNOT_CREATE_PARTICIPANT, correlationId());
    }
}