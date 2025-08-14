package org.example.eventplannerbackend.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

@Data
public class EventDTO {
    private Long id;
    private String name;
    private LocalDate date;
    private String location;
    private String description;
    private Set<Long> participantIds;
}