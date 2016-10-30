package com.amhzing.participant.api.model;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CountryTest {

    private Validator validator;

    @Before
    public void init() {

        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        this.validator = vf.getValidator();
    }

    @Test
    public void should_not_violate_constraints() {
        final Country valid = Country.create("SE", "Sweden");

        Set<ConstraintViolation<Country>> violations = this.validator.validate(valid);

        assertThat(violations).isEmpty();
    }

    @Test
    public void should_violate_constraints() {
        final Country invalid = Country.create("", "");

        Set<ConstraintViolation<Country>> violations = this.validator.validate(invalid);

        assertThat(violations).hasSize(2);
    }
}