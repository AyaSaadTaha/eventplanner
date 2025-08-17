package org.example.eventplannerbackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.eventplannerbackend.dto.ParticipantDTO;
import org.example.eventplannerbackend.service.ParticipantService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST Controller for managing participants.
 */
@RestController
@RequestMapping("/api/participants")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService participantService;

    /**
     * Creates a new participant.
     * @param participantDTO The participant data transfer object.
     * @return The created ParticipantDTO.
     */
    @PostMapping
    public ResponseEntity<ParticipantDTO> createParticipant(@Valid @RequestBody ParticipantDTO participantDTO) {
        return ResponseEntity.ok(participantService.createParticipant(participantDTO));
    }

    /**
     * Retrieves all participants.
     * @return A list of all ParticipantDTOs.
     */
    @GetMapping
    public ResponseEntity<List<ParticipantDTO>> getAllParticipants() {
        return ResponseEntity.ok(participantService.getAllParticipants());
    }

    /**
     * Retrieves a participant by their ID.
     * @param id The ID of the participant.
     * @return The ParticipantDTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ParticipantDTO> getParticipantById(@PathVariable Long id) {
        return ResponseEntity.ok(participantService.getParticipantById(id));
    }

    /**
     * Updates an existing participant.
     * @param id The ID of the participant.
     * @param participantDTO The updated participant data.
     * @return The updated ParticipantDTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ParticipantDTO> updateParticipant(@PathVariable Long id, @Valid @RequestBody ParticipantDTO participantDTO) {
        return ResponseEntity.ok(participantService.updateParticipant(id, participantDTO));
    }

    /**
     * Deletes a participant by their ID.
     * @param id The ID of the participant.
     * @return A response with no content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable Long id) {
        participantService.deleteParticipant(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Registers a participant for an event.
     * @param participantId The ID of the participant.
     * @param eventId The ID of the event.
     * @return The updated ParticipantDTO.
     */
    @PostMapping("/{participantId}/register/{eventId}")
    public ResponseEntity<ParticipantDTO> registerForEvent(
            @PathVariable Long participantId,
            @PathVariable Long eventId) {
        return ResponseEntity.ok(
                participantService.addParticipantToEvent(participantId, eventId)
        );
    }

    /**
     * Unregisters a participant from an event.
     * @param participantId The ID of the participant.
     * @param eventId The ID of the event.
     * @return The updated ParticipantDTO.
     */
    @DeleteMapping("/{participantId}/unregister/{eventId}")
    public ResponseEntity<ParticipantDTO> unregisterFromEvent(
            @PathVariable Long participantId,
            @PathVariable Long eventId) {
        return ResponseEntity.ok(
                participantService.removeParticipantFromEvent(participantId, eventId)
        );
    }
}
