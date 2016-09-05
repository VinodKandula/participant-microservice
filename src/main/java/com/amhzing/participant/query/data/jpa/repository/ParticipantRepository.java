package com.amhzing.participant.query.data.jpa.repository;

import com.amhzing.participant.query.data.jpa.mapping.ParticipantDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<ParticipantDetails, Long> {

    List<ParticipantDetails> findByAddress_CountryAllIgnoreCase(String country);

    List<ParticipantDetails> findByAddress_CountryAndAddress_CityAllIgnoreCase(String country, String city);

    List<ParticipantDetails> findByAddress_CountryAndAddress_CityAndAddress_AddressLine1AllIgnoreCase(String country, String city, String addressLine1);

    List<ParticipantDetails> findByAddress_CountryAndAddress_CityAndAddress_AddressLine1AndName_LastNameAllIgnoreCase(String country,
                                                                                                                      String city,
                                                                                                                      String addressLine1,
                                                                                                                      String lastName);

    List<ParticipantDetails> findByAddress_CountryAndAddress_CityAndAddress_AddressLine1AndName_LastNameAndParticipantIdAllIgnoreCase(String country,
                                                                                                                                      String city,
                                                                                                                                      String addressLine1,
                                                                                                                                      String lastName,
                                                                                                                                      String participantId);
}
