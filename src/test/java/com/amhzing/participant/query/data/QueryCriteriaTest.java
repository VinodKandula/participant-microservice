package com.amhzing.participant.query.data;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

import static com.amhzing.participant.helper.JUnitParamHelper.invalidMatching;
import static com.amhzing.participant.helper.JUnitParamHelper.valid;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class QueryCriteriaTest {

    @Test
    @Parameters(method = "values")
    public void should_validate_values(final Class<? extends Exception> exception,
                                        final String country,
                                        final String city,
                                        final String addressLine1,
                                        final String lastName,
                                        final String participantId) {
        try {
            QueryCriteria.create(country, city, addressLine1, lastName, participantId);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    private Object values() {
        return new Object[][]{
                {valid(), country(), city(), addressLine1(), lastName(), participantId()},
                {valid(), country(), null, null, null, null},
                {invalidMatching(IllegalArgumentException.class), "", city(), addressLine1(), lastName(), participantId()},
                {invalidMatching(NullPointerException.class), null, city(), addressLine1(), lastName(), participantId()}
        };
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
        return UUID.randomUUID().toString();
    }
}