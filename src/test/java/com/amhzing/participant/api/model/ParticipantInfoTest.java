package com.amhzing.participant.api.model;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ParticipantInfoTest {

    private Validator validator;

    @Before
    public void init() {

        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        this.validator = vf.getValidator();
    }

    @Test
    public void should_not_violate_constraints() {
        final ParticipantInfo valid = ParticipantInfo.create("123",
                                                             name(),
                                                             address(),
                                                             contactNumber(),
                                                             email());

        Set<ConstraintViolation<ParticipantInfo>> violations = this.validator.validate(valid);

        assertThat(violations).isEmpty();
    }

    @Test
    public void should_violate_constraints() {
        final ParticipantInfo invalid = ParticipantInfo.create("",
                                                               null,
                                                               null,
                                                               contactNumber(),
                                                               email());

        Set<ConstraintViolation<ParticipantInfo>> violations = this.validator.validate(invalid);
        assertThat(violations).hasSize(3);
    }

    private Address address() {
        return Address.create("ad1", "ad2", "city", "pCode", Country.create("SE", ""));
    }

    private Name name() {
        return Name.create("fname", "mName", "lName", "II");
    }

    private ContactNumber contactNumber() {
        return ContactNumber.create("12345678");
    }

    private Email email() {
        return Email.create("test@example.com");
    }
}