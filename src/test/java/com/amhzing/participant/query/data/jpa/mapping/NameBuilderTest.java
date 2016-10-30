package com.amhzing.participant.query.data.jpa.mapping;

import org.junit.Test;

import static com.amhzing.participant.helper.ParticipantDetailsHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

public class NameBuilderTest {

    @Test
    public void should_build_name() {
        assertThat(builder()).isEqualTo(name());
    }

    private Name builder() {
        return new NameBuilder().setFirstName(firstName())
                                .setMiddleName(middleName())
                                .setLastName(lastName())
                                .setSuffix(suffix())
                                .createName();
    }

    private Name name() {
        return Name.create(firstName(), middleName(), lastName(), suffix());
    }
}