package com.amhzing.participant.web.request;

import com.amhzing.participant.annotation.TestOffline;
import com.amhzing.participant.helper.JsonLoader;
import com.google.common.collect.ImmutableSet;
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

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
@TestOffline
public class ParticipantIdsTest {

    @Autowired
    private JacksonTester<ParticipantIds> json;

    @Autowired
    private ResourceLoader resourceLoader;

    private JsonLoader jsonLoader;

    @Before
    public void setUp() throws Exception {
        jsonLoader = new JsonLoader();
    }

    @Test
    public void should_serialize_request() throws Exception {
        final ParticipantIds request = new ParticipantIds(ImmutableSet.of(participantId()));

        final JsonContent<ParticipantIds> jsonContent = this.json.write(request);

        assertThat(jsonContent).hasJsonPathArrayValue("@.id");

        final JSONArray ids = JsonPath.read(jsonContent.getJson(), "@.id");
        assertThat(ids).hasSize(1);
        assertThat(ids.get(0)).isEqualTo(participantId());
    }

    public String participantId() {
        return "123456789";
    }
}