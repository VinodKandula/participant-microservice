package com.amhzing.participant.api.request;

import com.amhzing.participant.api.model.*;
import com.amhzing.participant.helper.JsonLoader;
import com.amhzing.participant.web.request.CreateParticipantRequest;
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
public class CreateParticipantRequestTest {

    @Autowired
    private JacksonTester<CreateParticipantRequest> json;

    @Autowired
    private ResourceLoader resourceLoader;

    private JsonLoader jsonLoader;

    @Before
    public void setup() throws Exception {
        jsonLoader = new JsonLoader();
    }

    @Test
    public void should_serialize_request() throws Exception {
        final CreateParticipantRequest request = CreateParticipantRequest.create(name(), address(),
                                                                                 contactNumber(), email(), user());

        final JsonContent<CreateParticipantRequest> jsonContent = this.json.write(request);

        assertAddress(jsonContent);
        assertContactNumber(jsonContent);
        assertEmail(jsonContent);
        assertName(jsonContent);
        assertUser(jsonContent);
    }

    @Test
    public void should_serialize_request_matching_json_file() throws Exception {
        final CreateParticipantRequest request = CreateParticipantRequest.create(name(), address(),
                                                                                 contactNumber(), email(), user());

        final Resource resource = resourceLoader.getResource("classpath:create-participant-request.json");
        assertThat(this.json.write(request)).isEqualToJson(resource);
    }

    @Test
    public void should_deserialize_request() throws Exception {
        final Resource resource = resourceLoader.getResource("classpath:create-participant-request.json");
        final String jsonContent = jsonLoader.getJson(resource);

        final CreateParticipantRequest request = CreateParticipantRequest.create(name(), address(),
                                                                                 contactNumber(), email(), user());

        assertThat(this.json.parse(jsonContent)).isEqualTo(request);
    }

    private void assertUser(final JsonContent<CreateParticipantRequest> jsonContent) {
        assertThat(jsonContent).hasJsonPathStringValue("@.user.userId");
        assertThat(jsonContent).extractingJsonPathStringValue("@.user.userId").isEqualTo(user().getUserId());
    }

    private void assertName(final JsonContent<CreateParticipantRequest> jsonContent) {
        assertThat(jsonContent).hasJsonPathStringValue("@.name.firstName");
        assertThat(jsonContent).extractingJsonPathStringValue("@.name.firstName").isEqualTo(name().getFirstName());

        assertThat(jsonContent).hasJsonPathStringValue("@.name.middleName");
        assertThat(jsonContent).extractingJsonPathStringValue("@.name.middleName").isEqualTo(name().getMiddleName());

        assertThat(jsonContent).hasJsonPathStringValue("@.name.lastName");
        assertThat(jsonContent).extractingJsonPathStringValue("@.name.lastName").isEqualTo(name().getLastName());

        assertThat(jsonContent).hasJsonPathStringValue("@.name.suffix");
        assertThat(jsonContent).extractingJsonPathStringValue("@.name.suffix").isEqualTo(name().getSuffix());
    }

    private void assertContactNumber(final JsonContent<CreateParticipantRequest> jsonContent) {
        assertThat(jsonContent).hasJsonPathStringValue("@.contactNumber.primaryNumber");
        assertThat(jsonContent).extractingJsonPathStringValue("@.contactNumber.primaryNumber").isEqualTo(contactNumber().getValue());
    }

    private void assertAddress(final JsonContent<CreateParticipantRequest> jsonContent) {
        assertThat(jsonContent).hasJsonPathStringValue("@.address.addressLine1");
        assertThat(jsonContent).extractingJsonPathStringValue("@.address.addressLine1").isEqualTo(address().getAddressLine1());

        assertThat(jsonContent).hasJsonPathStringValue("@.address.addressLine2");
        assertThat(jsonContent).extractingJsonPathStringValue("@.address.addressLine2").isEqualTo(address().getAddressLine2());

        assertThat(jsonContent).hasJsonPathStringValue("@.address.city");
        assertThat(jsonContent).extractingJsonPathStringValue("@.address.city").isEqualTo(address().getCity());

        assertThat(jsonContent).hasJsonPathStringValue("@.address.country.code");
        assertThat(jsonContent).extractingJsonPathStringValue("@.address.country.code").isEqualTo(address().getCountry().getCode());

        assertThat(jsonContent).hasJsonPathStringValue("@.address.country.name");
        assertThat(jsonContent).extractingJsonPathStringValue("@.address.country.name").isEqualTo(address().getCountry().getName());

        assertThat(jsonContent).hasJsonPathStringValue("@.address.postalCode");
        assertThat(jsonContent).extractingJsonPathStringValue("@.address.postalCode").isEqualTo(address().getPostalCode());
    }

    private void assertEmail(final JsonContent<CreateParticipantRequest> jsonContent) {
        assertThat(jsonContent).hasJsonPathStringValue("@.email.primaryEmail");
        assertThat(jsonContent).extractingJsonPathStringValue("@.email.primaryEmail").isEqualTo(email().getValue());
    }

    private Name name() {
        return Name.create("John", "Michael","Doe","II");
    }

    private Address address() {
        return Address.create("Elm Street 1", "c/o Freddy Cougar", "Nashville", "10291", Country.create("US", "United States"));
    }

    private ContactNumber contactNumber() {
        return ContactNumber.create("0749228216");
    }

    private Email email() {
        return Email.create("test-command@example.com");
    }

    private User user() {
        return User.create("myUserid");
    }
}