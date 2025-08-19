package org.example.eventplannerbackend.exception;

import org.example.eventplannerbackend.dto.EventDTO;
import org.example.eventplannerbackend.dto.ParticipantDTO;
import org.example.eventplannerbackend.model.Event;
import org.example.eventplannerbackend.model.Participant;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // تعيين Event إلى EventDTO
        mapper.createTypeMap(Event.class, EventDTO.class)
                .addMappings(m -> {
                    m.map(Event::getId, EventDTO::setId);
                    m.map(Event::getName, EventDTO::setName);
                    m.map(Event::getDate, EventDTO::setDate);
                    m.map(Event::getLocation, EventDTO::setLocation);
                    m.map(Event::getDescription, EventDTO::setDescription);
                    m.skip(EventDTO::setParticipantIds); // تخطي تعيين المشاركين
                });

        // تعيين Participant إلى ParticipantDTO
        mapper.createTypeMap(Participant.class, ParticipantDTO.class)
                .addMappings(m -> {
                    m.map(Participant::getId, ParticipantDTO::setId);
                    m.map(Participant::getFirstName, ParticipantDTO::setFirstName);
                    m.map(Participant::getLastName, ParticipantDTO::setLastName);
                    m.map(Participant::getEmail, ParticipantDTO::setEmail);
                    m.skip(ParticipantDTO::setEventIds); // تخطي تعيين الأحداث
                });

        return mapper;
    }
    /*@Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // Event → EventDTO
        mapper.createTypeMap(Event.class, EventDTO.class)
                .addMappings(m -> m.map(
                        src -> src.getParticipants() != null ?
                                src.getParticipants().stream()
                                        .map(Participant::getId)
                                        .collect(Collectors.toSet()) :
                                Collections.emptySet(),
                        EventDTO::setParticipantIds
                ));

        // Participant → ParticipantDTO
        mapper.createTypeMap(Participant.class, ParticipantDTO.class)
                .addMappings(m -> m.map(
                        src -> src.getEvents() != null ?
                                src.getEvents().stream()
                                        .map(Event::getId)
                                        .collect(Collectors.toSet()) :
                                Collections.emptySet(),
                        ParticipantDTO::setEventIds
                ));

        return mapper;
    }*/
}