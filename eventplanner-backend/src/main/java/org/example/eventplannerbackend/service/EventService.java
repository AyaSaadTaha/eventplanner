package org.example.eventplannerbackend.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.eventplannerbackend.dto.EventDTO;
import org.example.eventplannerbackend.dto.EventParticipantDTO;
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
public class EventService {
    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;
    private final ModelMapper modelMapper;

    // Event erstellen
    public EventDTO createEvent(EventDTO eventDTO) {
        Event event = modelMapper.map(eventDTO, Event.class);
        Event savedEvent = eventRepository.save(event);
        return modelMapper.map(savedEvent, EventDTO.class);
    }

    // Alle Events abrufen
    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(event -> {
                    EventDTO dto = modelMapper.map(event, EventDTO.class);
                    dto.setParticipantIds(event.getParticipants().stream()
                            .map(Participant::getId)
                            .collect(Collectors.toSet()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // Event nach ID abrufen
    public EventDTO getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        return modelMapper.map(event, EventDTO.class);
    }

    // Event aktualisieren
    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        modelMapper.map(eventDTO, existingEvent);
        Event updatedEvent = eventRepository.save(existingEvent);
        return modelMapper.map(updatedEvent, EventDTO.class);
    }

    // Event löschen
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    // Teilnehmern im Event
    @Transactional
    public Set<ParticipantDTO> getEventParticipants(Long eventId) {
        Event event = eventRepository.findByIdWithParticipants(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));

        return event.getParticipants().stream()
                .map(participant -> {
                    ParticipantDTO dto = modelMapper.map(participant, ParticipantDTO.class);
                    dto.setEventIds(participant.getEvents().stream()
                            .map(Event::getId)
                            .collect(Collectors.toSet()));
                    return dto;
                })
                .collect(Collectors.toSet());
    }

    // Teilnehmer zu Event hinzufügen
    public EventDTO addParticipant(Long eventId, Long participantId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new ResourceNotFoundException("Participant not found"));

        event.getParticipants().add(participant);
        Event updatedEvent = eventRepository.save(event);
        return modelMapper.map(updatedEvent, EventDTO.class);
    }

    // Teilnehmer von Event entfernen
    public EventDTO removeParticipant(Long eventId, Long participantId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        event.getParticipants().removeIf(p -> p.getId().equals(participantId));
        Event updatedEvent = eventRepository.save(event);
        return modelMapper.map(updatedEvent, EventDTO.class);
    }
}