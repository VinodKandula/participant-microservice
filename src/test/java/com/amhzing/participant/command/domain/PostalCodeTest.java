package com.amhzing.participant.command.domain;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.amhzing.participant.command.domain.PostalCode.MAX_LENGTH;
import static com.amhzing.participant.helper.JUnitParamHelper.invalidMatching;
import static com.amhzing.participant.helper.JUnitParamHelper.valid;
import static org.apache.commons.lang.StringUtils.repeat;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class PostalCodeTest {

    @Test
    @Parameters(method = "postalCodeValues")
    public void postal_code_is_valid(final Class<? extends Exception> exception, final String value)  {
        try {
            PostalCode.create(value);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @SuppressWarnings("unused")
    private Object postalCodeValues() {
        return new Object[][]{
                {valid(), "12334"},
                {invalidMatching(IllegalArgumentException.class), ""},
                {invalidMatching(IllegalArgumentException.class), repeat("x", MAX_LENGTH + 1)},
                {invalidMatching(NullPointerException.class), null}
        };
    }
}