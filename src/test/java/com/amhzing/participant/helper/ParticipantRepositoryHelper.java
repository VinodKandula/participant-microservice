package com.amhzing.participant.helper;

import com.amhzing.participant.query.data.jpa.mapping.Address;
import com.amhzing.participant.query.data.jpa.mapping.Name;
import com.amhzing.participant.query.data.jpa.mapping.ParticipantDetails;
import com.amhzing.participant.query.data.jpa.mapping.ParticipantDetailsBuilder;

import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public final class ParticipantRepositoryHelper {

    private ParticipantRepositoryHelper() {
    }

    public static void assertParticipantDetails(final List<ParticipantDetails> participants) {
        assertThat(participants).isNotNull();
        assertThat(participants).hasSize(1);

        assertThat(participants.get(0).getName()).isEqualTo(name());
        assertThat(participants.get(0).getAddress()).isEqualTo(address());
        assertThat(participants.get(0).getContactNumber()).isEqualTo(contactNumber());
        assertThat(participants.get(0).getEmail()).isEqualTo(email());
    }

    public static ParticipantDetails participant(final UUID participantId) {
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

    private static Name name() {
        return Name.create("John", "B.", "Doe", "II");
    }

    private static Address address() {
        return Address.create("Elm Street 55", "", "Nashville", "BT2 1GL", "US");
    }

    private static String email() {
        return "test@example.com";
    }
    private static String contactNumber() {
        return "073994458";
    }

    private static Timestamp currentTime() {
        final ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        return Timestamp.from(utc.toInstant());
    }
}
