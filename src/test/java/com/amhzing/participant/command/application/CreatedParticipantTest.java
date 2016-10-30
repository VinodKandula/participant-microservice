package com.amhzing.participant.command.application;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.amhzing.participant.helper.JUnitParamHelper.invalidMatching;
import static com.amhzing.participant.helper.JUnitParamHelper.valid;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class CreatedParticipantTest {

    @Test
    @Parameters(method = "values")
    public void should_validate(final Class<? extends Exception> exception, final String participantId)  {
        try {
            CreatedParticipant.create(participantId);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    private Object values() {
        return new Object[][]{
                {valid(), "1234-12398-fsdf"},
                {invalidMatching(IllegalArgumentException.class), ""},
                {invalidMatching(NullPointerException.class), null}
        };
    }
}