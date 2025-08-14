package org.example.eventplannerbackend.repository;

import  org.example.eventplannerbackend.model.Event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e FROM Event e LEFT JOIN FETCH e.participants WHERE e.id = :eventId")
    Optional<Event> findByIdWithParticipants(@Param("eventId") Long eventId);
}