package com.amhzing.participant.integrationtest;

import com.amhzing.participant.ParticipantApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ParticipantApplication.class)
@WebIntegrationTest
public class IntegrationTest {

    @Test
    public void contextLoads() {
    }

}