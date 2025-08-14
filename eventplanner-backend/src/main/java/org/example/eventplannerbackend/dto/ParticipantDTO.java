package org.example.eventplannerbackend.dto;

import lombok.Data;
import java.util.Set;

@Data
public class ParticipantDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<Long> eventIds;
}