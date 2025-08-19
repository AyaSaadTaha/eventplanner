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
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;
    private final ModelMapper modelMapper;

    /**
     * Creates a new event.
     * @param eventDTO The event data transfer object.
     * @return The created EventDTO.
     */
    public EventDTO createEvent(EventDTO eventDTO) {
        Event event = modelMapper.map(eventDTO, Event.class);
        Event savedEvent = eventRepository.save(event);
        return modelMapper.map(savedEvent, EventDTO.class);
    }

    /**
     * Retrieves all events.
     * @return A list of all EventDTOs.
     */
    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(event -> modelMapper.map(event, EventDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves an event by its ID.
     * @param id The ID of the event.
     * @return The EventDTO.
     */
    public EventDTO getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        return modelMapper.map(event, EventDTO.class);
    }

    /**
     * Updates an existing event.
     * @param id The ID of the event to update.
     * @param eventDTO The updated event data.
     * @return The updated EventDTO.
     */
    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        existingEvent.setName(eventDTO.getName());
        existingEvent.setDate(eventDTO.getDate());
        existingEvent.setLocation(eventDTO.getLocation());
        existingEvent.setDescription(eventDTO.getDescription());

        Event updatedEvent = eventRepository.save(existingEvent);
        return modelMapper.map(updatedEvent, EventDTO.class);
    }

    /**
     * Deletes an event by its ID.
     * @param id The ID of the event to delete.
     */
    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event not found");
        }
        eventRepository.deleteById(id);
    }

    /**
     * Retrieves all participants for a specific event.
     * @param eventId The ID of the event.
     * @return A set of ParticipantDTOs.
     */
    public Set<ParticipantDTO> getEventParticipants(Long eventId) {
        Event event = eventRepository.findByIdWithParticipants(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        return event.getParticipants().stream()
                .map(participant -> modelMapper.map(participant, ParticipantDTO.class))
                .collect(Collectors.toSet());
    }

    /**
     * Adds a participant to an event.
     * @param eventId The ID of the event.
     * @param participantId The ID of the participant to add.
     * @return The updated EventDTO.
     */
    public EventDTO addParticipant(Long eventId, Long participantId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new ResourceNotFoundException("Participant not found"));

        event.getParticipants().add(participant);
        // Important: You need to manage both sides of the bidirectional relationship
        participant.getEvents().add(event);

        eventRepository.save(event);
        return modelMapper.map(event, EventDTO.class);
    }

    /**
     * Removes a participant from an event.
     * @param eventId The ID of the event.
     * @param participantId The ID of the participant to remove.
     * @return The updated EventDTO.
     */
    public EventDTO removeParticipant(Long eventId, Long participantId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new ResourceNotFoundException("Participant not found"));

        event.getParticipants().remove(participant);
        // Important: You need to manage both sides of the bidirectional relationship
        participant.getEvents().remove(event);

        eventRepository.save(event);
        return modelMapper.map(event, EventDTO.class);
    }
}
