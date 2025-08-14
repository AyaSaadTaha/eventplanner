package org.example.eventplannerbackend.service;


import lombok.RequiredArgsConstructor;
import org.example.eventplannerbackend.dto.ParticipantDTO;
import org.example.eventplannerbackend.exception.ResourceNotFoundException;
import org.example.eventplannerbackend.model.Event;
import org.example.eventplannerbackend.model.Participant;
import org.example.eventplannerbackend.repository.EventRepository;
import org.example.eventplannerbackend.repository.ParticipantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ParticipantService {
    private final ParticipantRepository participantRepository;
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;

    // Teilnehmer erstellen
    public ParticipantDTO createParticipant(ParticipantDTO participantDTO) {
        Participant participant = modelMapper.map(participantDTO, Participant.class);
        Participant savedParticipant = participantRepository.save(participant);
        return modelMapper.map(savedParticipant, ParticipantDTO.class);
    }

    // Alle Teilnehmer abrufen
    public List<ParticipantDTO> getAllParticipants() {
        return participantRepository.findAll().stream()
                .map(participant -> {
                    ParticipantDTO dto = modelMapper.map(participant, ParticipantDTO.class);
                    dto.setEventIds(participant.getEvents().stream()
                            .map(Event::getId)
                            .collect(Collectors.toSet()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // Teilnehmer nach ID abrufen
    public ParticipantDTO getParticipantById(Long id) {
        Participant participant = participantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participant not found"));
        return modelMapper.map(participant, ParticipantDTO.class);
    }

    // Teilnehmer aktualisieren
    public ParticipantDTO updateParticipant(Long id, ParticipantDTO participantDTO) {
        Participant existingParticipant = participantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participant not found"));

        modelMapper.map(participantDTO, existingParticipant);
        Participant updatedParticipant = participantRepository.save(existingParticipant);
        return modelMapper.map(updatedParticipant, ParticipantDTO.class);
    }

    // Teilnehmer löschen
    public void deleteParticipant(Long id) {
        participantRepository.deleteById(id);
    }

    public ParticipantDTO addParticipantToEvent(Long participantId, Long eventId) {
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new ResourceNotFoundException("Participant not found with id: " + participantId));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));

        // Bidirektionale Beziehung pflegen
        participant.getEvents().add(event);
        event.getParticipants().add(participant);

        participantRepository.save(participant);
        eventRepository.save(event);

        return modelMapper.map(participant, ParticipantDTO.class);
    }

    public ParticipantDTO removeParticipantFromEvent(Long participantId, Long eventId) {
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new ResourceNotFoundException("Participant not found with id: " + participantId));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));

        // Bidirektionale Beziehung auflösen
        participant.getEvents().remove(event);
        event.getParticipants().remove(participant);

        participantRepository.save(participant);
        eventRepository.save(event);

        return modelMapper.map(participant, ParticipantDTO.class);
    }
}
