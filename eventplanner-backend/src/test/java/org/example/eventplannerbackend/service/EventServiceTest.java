/*
package org.example.eventplannerbackend.service;


import org.example.eventplannerbackend.model.Event;
import org.example.eventplannerbackend.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    public void createEvent_validEvent_returnsSavedEvent() {
        Event event = new Event();
        event.setName("Test Event");
        event.setDate(LocalDate.now().plusDays(1));
        event.setLocation("Test Location");

        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event savedEvent = eventService.createEvent(event);

        assertNotNull(savedEvent);
        assertEquals("Test Event", savedEvent.getName());
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    public void deleteEvent_existingId_deletesEvent() {
        Long eventId = 1L;
        Event event = new Event();
        event.setId(eventId);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        doNothing().when(eventRepository).delete(event);

        eventService.deleteEvent(eventId);

        verify(eventRepository, times(1)).delete(event);
    }
}*/
