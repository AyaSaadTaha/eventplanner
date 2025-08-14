package org.example.eventplannerbackend.controller;


import org.example.eventplannerbackend.model.Event;
import org.example.eventplannerbackend.repository.EventRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventRepository eventRepository;

    @AfterEach
    public void resetDb() {
        eventRepository.deleteAll();
    }

    @Test
    public void createEvent_thenGetEvent() throws Exception {
        Event event = new Event();
        event.setName("Integration Test Event");
        event.setDate(LocalDate.now().plusDays(1));
        event.setLocation("Test Location");

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Integration Test Event\",\"date\":\"" + LocalDate.now().plusDays(1) + "\",\"location\":\"Test Location\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Integration Test Event")));

        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Integration Test Event")));
    }
}