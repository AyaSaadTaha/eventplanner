package org.example.eventplannerbackend.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Event name is required")
    @Size(max = 100, message = "Event name must be less than 100 characters")
    private String name;

    @NotNull(message = "Event date is required")
    @Future(message = "Event date must be in the future")
    private LocalDate date;

    @NotBlank(message = "Location is required")
    @Size(max = 100, message = "Location must be less than 100 characters")
    private String location;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "event_participants",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    @JsonManagedReference
    private Set<Participant> participants = new HashSet<>();
}