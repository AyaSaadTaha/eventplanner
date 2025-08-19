package org.example.eventplannerbackend.repository;

import org.example.eventplannerbackend.model.Event;
import org.example.eventplannerbackend.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    @Query("SELECT p FROM Participant p LEFT JOIN FETCH p.events WHERE p.id = :participantId")
    Optional<Participant> findByIdWithParticipants(@Param("participantId") Long participantId);
}
