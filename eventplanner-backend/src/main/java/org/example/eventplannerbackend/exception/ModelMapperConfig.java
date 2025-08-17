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

        // Event to EventDTO mapping
        mapper.createTypeMap(Event.class, EventDTO.class)
                .addMappings(m -> m.map(
                        src -> src.getParticipants() != null ?
                                src.getParticipants().stream().map(Participant::getId).collect(Collectors.toSet()) :
                                Collections.emptySet(),
                        EventDTO::setParticipantIds
                ));

        // Participant to ParticipantDTO mapping
        mapper.createTypeMap(Participant.class, ParticipantDTO.class)
                .addMappings(m -> m.map(
                        src -> src.getEvents() != null ?
                                src.getEvents().stream().map(Event::getId).collect(Collectors.toSet()) :
                                Collections.emptySet(),
                        ParticipantDTO::setEventIds
                ));

        return mapper;
    }
}