package org.example.eventplannerbackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.eventplannerbackend.dto.EventDTO;
import org.example.eventplannerbackend.dto.EventParticipantDTO;
import org.example.eventplannerbackend.dto.ParticipantDTO;
import org.example.eventplannerbackend.model.Event;
import org.example.eventplannerbackend.model.Participant;
import org.example.eventplannerbackend.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor

public class EventController {
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventDTO eventDTO) {
        return ResponseEntity.ok(eventService.createEvent(eventDTO));
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @Valid @RequestBody EventDTO eventDTO) {
        return ResponseEntity.ok(eventService.updateEvent(id, eventDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{eventId}/participants")
    public ResponseEntity<Set<ParticipantDTO>> getEventParticipants(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.getEventParticipants(eventId));
    }

    @PostMapping("/{eventId}/participants/{participantId}")
    public ResponseEntity<EventDTO> addParticipant(@PathVariable Long eventId, @PathVariable Long participantId) {
        return ResponseEntity.ok(eventService.addParticipant(eventId, participantId));
    }

    @DeleteMapping("/{eventId}/participants/{participantId}")
    public ResponseEntity<EventDTO> removeParticipant(@PathVariable Long eventId, @PathVariable Long participantId) {
        return ResponseEntity.ok(eventService.removeParticipant(eventId, participantId));
    }
}