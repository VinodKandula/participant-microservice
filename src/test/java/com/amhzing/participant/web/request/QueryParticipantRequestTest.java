package com.amhzing.participant.web.request;

import com.amhzing.participant.annotation.TestOffline;
import com.amhzing.participant.helper.JsonLoader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
@TestOffline
public class QueryParticipantRequestTest {

    @Autowired
    private JacksonTester<QueryParticipantRequest> json;

    @Autowired
    private ResourceLoader resourceLoader;

    private JsonLoader jsonLoader;

    @Before
    public void setUp() throws Exception {
        jsonLoader = new JsonLoader();
    }

    @Test
    public void should_serialize_request() throws Exception {
        final QueryParticipantRequest request = QueryParticipantRequest.create(country(), city(), addressLine1(),
                                                                               lastName(), participantId());

        final JsonContent<QueryParticipantRequest> jsonContent = this.json.write(request);

        assertRequestValues(jsonContent);
    }

    @Test
    public void should_serialize_request_matching_json_file() throws Exception {
        final QueryParticipantRequest request = QueryParticipantRequest.create(country(), city(), addressLine1(),
                                                                               lastName(), participantId());

        final Resource resource = resourceLoader.getResource("classpath:query-participant-request.json");
        assertThat(this.json.write(request)).isEqualToJson(resource);
    }

    @Test
    public void should_deserialize_request() throws Exception {
        final Resource resource = resourceLoader.getResource("classpath:query-participant-request.json");
        final String jsonContent = jsonLoader.getJson(resource);

        final QueryParticipantRequest request = QueryParticipantRequest.create(country(), city(), addressLine1(),
                                                                               lastName(), participantId());

        assertThat(this.json.parse(jsonContent)).isEqualTo(request);
    }

    private void assertRequestValues(final JsonContent<QueryParticipantRequest> jsonContent) {
        assertThat(jsonContent).extractingJsonPathStringValue("@.country").isEqualTo(country());
        assertThat(jsonContent).extractingJsonPathStringValue("@.city").isEqualTo(city());
        assertThat(jsonContent).extractingJsonPathStringValue("@.addressLine1").isEqualTo(addressLine1());
        assertThat(jsonContent).extractingJsonPathStringValue("@.lastName").isEqualTo(lastName());
        assertThat(jsonContent).extractingJsonPathStringValue("@.participantId").isEqualTo(participantId());
    }

    private String country() {
        return "SE";
    }

    private String city() {
        return "city";
    }

    private String addressLine1() {
        return "ad1";
    }

    private String lastName() {
        return "lName";
    }

    private String participantId() {
        return "12345";
    }
}