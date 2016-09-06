package com.amhzing.participant.query.data.jpa.repository;

import com.amhzing.participant.query.data.jpa.mapping.ParticipantDetails;
import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

public interface ParticipantQueryDslRepository extends JpaRepository<ParticipantDetails, Long>, QueryDslPredicateExecutor<ParticipantDetails> {

    List<ParticipantDetails> findAll(Predicate predicate);
}
