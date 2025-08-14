package org.example.eventplannerbackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.eventplannerbackend.dto.ParticipantDTO;
import org.example.eventplannerbackend.model.Participant;
import org.example.eventplannerbackend.service.ParticipantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/participants")
@RequiredArgsConstructor

public class ParticipantController {
    private final ParticipantService participantService;

    @PostMapping
    public ResponseEntity<ParticipantDTO> createParticipant(@Valid @RequestBody ParticipantDTO participantDTO) {
        return ResponseEntity.ok(participantService.createParticipant(participantDTO));
    }
    @GetMapping
    public ResponseEntity<List<ParticipantDTO>> getAllParticipants() {
        return ResponseEntity.ok(participantService.getAllParticipants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipantDTO> getParticipantById(@PathVariable Long id) {
        return ResponseEntity.ok(participantService.getParticipantById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ParticipantDTO> updateParticipant(@PathVariable Long id, @Valid @RequestBody ParticipantDTO participantDTO) {
        return ResponseEntity.ok(participantService.updateParticipant(id, participantDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable Long id) {
        participantService.deleteParticipant(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{participantId}/register/{eventId}")
    public ResponseEntity<ParticipantDTO> registerForEvent(
            @PathVariable Long participantId,
            @PathVariable Long eventId) {
        return ResponseEntity.ok(
                participantService.addParticipantToEvent(participantId, eventId)
        );
    }

    @DeleteMapping("/{participantId}/unregister/{eventId}")
    public ResponseEntity<ParticipantDTO> unregisterFromEvent(
            @PathVariable Long participantId,
            @PathVariable Long eventId) {
        return ResponseEntity.ok(
                participantService.removeParticipantFromEvent(participantId, eventId)
        );
    }
}