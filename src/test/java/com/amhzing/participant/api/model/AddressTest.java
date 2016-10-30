package com.amhzing.participant.api.model;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class AddressTest {

    private Validator validator;

    @Before
    public void init() {

        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        this.validator = vf.getValidator();
    }

    @Test
    public void should_not_violate_constraints() {
        final Address validAddress = Address.create(addressLine1(),
                                               addressLine2(),
                                               city(),
                                               postalCode(),
                                               Country.create(countryCode(), countryName()));

        Set<ConstraintViolation<Address>> violations = this.validator.validate(validAddress);

        assertThat(violations).isEmpty();
    }

    @Test
    public void should_violate_constraints() {
        final Address invalidAddress = Address.create(null,
                                                      addressLine2(),
                                                      null,
                                                      postalCode(),
                                                      null);

        Set<ConstraintViolation<Address>> violations = this.validator.validate(invalidAddress);

        assertThat(violations).hasSize(3);
    }

    private String postalCode() {
        return "pCode";
    }

    private String city() {
        return "city";
    }

    private String addressLine2() {
        return "ad2";
    }

    private String addressLine1() {
        return "ad1";
    }

    private String countryCode() {
        return "SE";
    }

    private String countryName() {
        return "Sweden";
    }
}