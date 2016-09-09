package com.amhzing.participant.query.data.jpa.repository;

import com.amhzing.participant.query.data.jpa.mapping.Address;
import com.amhzing.participant.query.data.jpa.mapping.Name;
import com.amhzing.participant.query.data.jpa.mapping.ParticipantDetails;
import com.amhzing.participant.query.data.jpa.mapping.ParticipantDetailsBuilder;
import com.fasterxml.uuid.Generators;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ParticipantRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ParticipantRepository repository;

    private UUID participantId = Generators.timeBasedGenerator().generate();

    @Test
    public void should_insert_participant() throws Exception {
        // This is a workaround as entityManager.persist expects @GeneratedValue on the @Id
        entityManager.getEntityManager().persist(participantDetails());
        final List<ParticipantDetails> participants = this.repository.findByAddress_CountryAllIgnoreCase("US");

        assertParticipantDetails(participants);
    }

    private void assertParticipantDetails(final List<ParticipantDetails> participants) {
        assertThat(participants).isNotNull();
        assertThat(participants).hasSize(1);

        assertThat(participants.get(0).getName()).isEqualTo(name());
        assertThat(participants.get(0).getAddress()).isEqualTo(address());
        assertThat(participants.get(0).getContactNumber()).isEqualTo(contactNumber());
        assertThat(participants.get(0).getEmail()).isEqualTo(email());
    }

    private ParticipantDetails participantDetails() {
        return new ParticipantDetailsBuilder().setParticipantId(participantId.toString())
                                              .setName(name())
                                              .setAddress(address())
                                              .setEmail(email())
                                              .setContactNumber(contactNumber())
                                              .setAddedDate(currentTime())
                                              .setAddedBy("testUser")
                                              .setUpdatedDate(currentTime())
                                              .setUpdatedBy("testUser")
                                              .create();
    }

    private Name name() {
        return Name.create("John", "B.", "Doe", "II");
    }

    private Address address() {
        return Address.create("Elm Street 55", "", "Nashville", "BT2 1GL", "US");
    }

    private String email() {
        return "test@example.com";
    }
    private String contactNumber() {
        return "073994458";
    }

    private Timestamp currentTime() {
        final ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        return Timestamp.from(utc.toInstant());
    }
}
