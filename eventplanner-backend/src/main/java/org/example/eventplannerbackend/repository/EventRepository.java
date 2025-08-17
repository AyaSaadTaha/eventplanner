package org.example.eventplannerbackend.repository;

import org.example.eventplannerbackend.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    /**
     * Retrieves an Event by its ID, eagerly fetching the participants.
     * This avoids LazyInitializationException when accessing participants outside of a transaction.
     * @param eventId The ID of the event.
     * @return An Optional containing the Event, or empty if not found.
     */
    @Query("SELECT e FROM Event e LEFT JOIN FETCH e.participants WHERE e.id = :eventId")
    Optional<Event> findByIdWithParticipants(@Param("eventId") Long eventId);
}
