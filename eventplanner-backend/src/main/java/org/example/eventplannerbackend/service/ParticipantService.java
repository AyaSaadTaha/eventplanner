package org.example.eventplannerbackend.service;


import lombok.RequiredArgsConstructor;
import org.example.eventplannerbackend.dto.ParticipantDTO;
import org.example.eventplannerbackend.exception.ResourceNotFoundException;
import org.example.eventplannerbackend.model.Event;
import org.example.eventplannerbackend.model.Participant;
import org.example.eventplannerbackend.repository.EventRepository;
import org.example.eventplannerbackend.repository.ParticipantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import jakarta.transaction.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;

    /**
     * Creates a new participant.
     * @param participantDTO The participant data transfer object.
     * @return The created ParticipantDTO.
     */
    public ParticipantDTO createParticipant(ParticipantDTO participantDTO) {
        Participant participant = modelMapper.map(participantDTO, Participant.class);
        Participant savedParticipant = participantRepository.save(participant);
        return modelMapper.map(savedParticipant, ParticipantDTO.class);
    }

    /**
     * Retrieves all participants.
     * @return A list of all ParticipantDTOs.
     */

    public List<ParticipantDTO> getAllParticipants() {
        return participantRepository.findAll().stream()
                .map(participant -> modelMapper.map(participant, ParticipantDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a participant by their ID.
     * @param id The ID of the participant.
     * @return The ParticipantDTO.
     */
    public ParticipantDTO getParticipantById(Long id) {
        Participant participant = participantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participant not found"));
        return modelMapper.map(participant, ParticipantDTO.class);
    }

    /**
     * Updates an existing participant.
     * @param id The ID of the participant to update.
     * @param participantDTO The updated participant data.
     * @return The updated ParticipantDTO.
     */
    public ParticipantDTO updateParticipant(Long id, ParticipantDTO participantDTO) {
        Participant existingParticipant = participantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participant not found"));

        existingParticipant.setFirstName(participantDTO.getFirstName());
        existingParticipant.setLastName(participantDTO.getLastName());
        existingParticipant.setEmail(participantDTO.getEmail());

        Participant updatedParticipant = participantRepository.save(existingParticipant);
        return modelMapper.map(updatedParticipant, ParticipantDTO.class);
    }

    /**
     * Deletes a participant by their ID.
     * @param id The ID of the participant to delete.
     */
    public void deleteParticipant(Long id) {
        if (!participantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Participant not found");
        }
        participantRepository.deleteById(id);
    }

    /**
     * Adds a participant to an event.
     * @param participantId The ID of the participant.
     * @param eventId The ID of the event.
     * @return The updated ParticipantDTO.
     */
    public ParticipantDTO addParticipantToEvent(Long participantId, Long eventId) {
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new ResourceNotFoundException("Participant not found with id: " + participantId));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));

        // Manages the bidirectional relationship on both sides
        participant.getEvents().add(event);
        event.getParticipants().add(participant);

        participantRepository.save(participant);
        eventRepository.save(event);

        return modelMapper.map(participant, ParticipantDTO.class);
    }

    /**
     * Removes a participant from an event.
     * @param participantId The ID of the participant.
     * @param eventId The ID of the event.
     * @return The updated ParticipantDTO.
     */
    public ParticipantDTO removeParticipantFromEvent(Long participantId, Long eventId) {
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new ResourceNotFoundException("Participant not found with id: " + participantId));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));

        // Resolves the bidirectional relationship on both sides
        participant.getEvents().remove(event);
        event.getParticipants().remove(participant);

        participantRepository.save(participant);
        eventRepository.save(event);

        return modelMapper.map(participant, ParticipantDTO.class);
    }
}
