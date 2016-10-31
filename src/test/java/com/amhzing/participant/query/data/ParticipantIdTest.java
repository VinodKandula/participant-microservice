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
public class ParticipantIdTest {

    @Test
    @Parameters(method = "values")
    public void validate_values(final Class<? extends Exception> exception, final UUID value)  {
        try {
            ParticipantId.create(value);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                {valid(), UUID.randomUUID()},
                {invalidMatching(NullPointerException.class), null}
        };
    }
}