package com.amhzing.participant.api.model;

import org.junit.Before;
import org.junit.Test;

import javax.validation.*;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class NameTest {

    private Validator validator;

    @Before
    public void init() {

        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        this.validator = vf.getValidator();
    }

    @Test
    public void should_not_violate_constraints() {
        final Name valid = Name.create(firstName(),
                                       middleName(),
                                       lastName(),
                                       suffix());

        Set<ConstraintViolation<Name>> violations = this.validator.validate(valid);

        assertThat(violations).isEmpty();
    }

    @Test
    public void should_violate_constraints() {
        final Name invalid = Name.create(firstName(),
                                         middleName(),
                                         null,
                                         suffix());

        Set<ConstraintViolation<Name>> violations = this.validator.validate(invalid);
        assertThat(violations).hasSize(1);

        final Optional<Path> firstViolation = violations.stream().map(violation -> violation.getPropertyPath()).findFirst();
        assertThat(firstViolation.isPresent()).isTrue();
        assertThat(firstViolation.get().iterator().next().getName()).isEqualToIgnoringCase("lastName");
    }

    private String firstName() {
        return "fName";
    }

    private String middleName() {
        return "mName";
    }

    private String lastName() {
        return "lName";
    }

    private String suffix() {
        return "IV";
    }
}