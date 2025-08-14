package org.example.eventplannerbackend.dto;

import lombok.Data;

@Data
public class EventParticipantDTO {
    private Long eventId;
    private Long participantId;
}