package com.amhzing.participant.query.data.jpa.repository;

import com.amhzing.participant.annotation.TestOffline;
import com.amhzing.participant.query.data.jpa.mapping.ParticipantDetails;
import com.fasterxml.uuid.Generators;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static com.amhzing.participant.query.data.jpa.mapping.QParticipantDetails.participantDetails;
import static com.amhzing.participant.helper.ParticipantRepositoryHelper.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestOffline
public class ParticipantQueryDslRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ParticipantQueryDslRepository repository;

    private UUID participantId = Generators.timeBasedGenerator().generate();

    @Test
    public void should_insert_participant() throws Exception {
        // This is a workaround as entityManager.persist expects @GeneratedValue on the @Id
        entityManager.getEntityManager().persist(participant(participantId));

        final BooleanExpression predicate = participantDetails.address.country.equalsIgnoreCase("us");

        final List<ParticipantDetails> participants = this.repository.findAll(predicate);

        assertParticipantDetails(participants);
    }
}
