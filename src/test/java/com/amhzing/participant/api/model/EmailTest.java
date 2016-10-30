package com.amhzing.participant.api.model;

import org.junit.Before;
import org.junit.Test;

import javax.validation.*;
import java.util.Optional;
import java.util.Set;

import static org.apache.commons.lang.StringUtils.repeat;
import static org.assertj.core.api.Assertions.assertThat;

public class EmailTest {

    private Validator validator;

    @Before
    public void init() {

        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        this.validator = vf.getValidator();
    }

    @Test
    public void should_not_violate_constraints() {
        final Email valid = Email.create("test@example.com");

        Set<ConstraintViolation<Email>> violations = this.validator.validate(valid);

        assertThat(violations).isEmpty();
    }

    @Test
    public void should_violate_constraints() {
        final Email invalid = Email.create(repeat("x", 51));

        Set<ConstraintViolation<Email>> violations = this.validator.validate(invalid);
        assertThat(violations).hasSize(1);

        final Optional<Path> firstViolation = violations.stream().map(violation -> violation.getPropertyPath()).findFirst();
        assertThat(firstViolation.isPresent()).isTrue();
        assertThat(firstViolation.get().iterator().next().getName()).isEqualToIgnoringCase("value");
    }
}