package org.example.eventplannerbackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.eventplannerbackend.dto.EventDTO;
import org.example.eventplannerbackend.dto.EventParticipantDTO;
import org.example.eventplannerbackend.dto.ParticipantDTO;
import org.example.eventplannerbackend.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

/**
 * REST Controller for managing events.
 */
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    /**
     * Creates a new event.
     * @param eventDTO The event data transfer object.
     * @return The created EventDTO.
     */
    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventDTO eventDTO) {
        return ResponseEntity.ok(eventService.createEvent(eventDTO));
    }

    /**
     * Retrieves all events.
     * @return A list of all EventDTOs.
     */
    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    /**
     * Retrieves an event by its ID.
     * @param id The ID of the event.
     * @return The EventDTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    /**
     * Updates an existing event.
     * @param id The ID of the event.
     * @param eventDTO The updated event data.
     * @return The updated EventDTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @Valid @RequestBody EventDTO eventDTO) {
        return ResponseEntity.ok(eventService.updateEvent(id, eventDTO));
    }

    /**
     * Deletes an event by its ID.
     * @param id The ID of the event.
     * @return A response with no content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves all participants for a specific event.
     * @param eventId The ID of the event.
     * @return A set of ParticipantDTOs.
     */
    @GetMapping("/{eventId}/participants")
    public ResponseEntity<Set<ParticipantDTO>> getEventParticipants(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.getEventParticipants(eventId));
    }

    /**
     * Adds a participant to an event.
     * Note: This method uses a custom DTO for clarity.
     * @param eventId The ID of the event.
     * @param participantId The ID of the participant.
     * @return The updated EventDTO.
     */
    @PostMapping("/{eventId}/participants/{participantId}")
    public ResponseEntity<EventDTO> addParticipant(@PathVariable Long eventId, @PathVariable Long participantId) {
        return ResponseEntity.ok(eventService.addParticipant(eventId, participantId));
    }


    /**
     * Removes a participant from an event.
     * Note: This method uses a custom DTO for clarity.
     * @param eventId The ID of the event.
     * @param participantId The ID of the participant.
     * @return The updated EventDTO.
     */
    @DeleteMapping("/{eventId}/participants/{participantId}")
    public ResponseEntity<EventDTO> removeParticipant(@PathVariable Long eventId, @PathVariable Long participantId) {
        return ResponseEntity.ok(eventService.removeParticipant(eventId, participantId));
    }
}
