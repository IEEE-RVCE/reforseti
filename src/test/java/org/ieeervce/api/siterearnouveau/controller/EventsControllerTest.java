package org.ieeervce.api.siterearnouveau.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ieeervce.api.siterearnouveau.entity.Event;
import org.ieeervce.api.siterearnouveau.service.EventsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class EventsControllerTest {
    private static final String KEYWORDS = "a b c d";

    private static final int EVENT_ID = 1;
    private static final String HOST_NAME = "abcd efgh";

    List<Event.Host> exampleHostsList = Collections.singletonList(new Event.Host());

    @Mock
    EventsService eventsService;

    @InjectMocks
    EventsController eventsController;

    static ObjectMapper objectMapper = new ObjectMapper();
    @Spy
    Event event;

    MockMvc mvc;

    @BeforeEach
    void setup() throws JsonProcessingException {
        mvc = MockMvcBuilders.standaloneSetup(eventsController).build();
        event.setEventId(EVENT_ID);
        event.setKeywords(KEYWORDS);
        event.setHosts(exampleHostsList);
        exampleHostsList.get(0).setName(HOST_NAME);
    }

    @Test
    void testList() throws Exception {
        when(eventsService.list()).thenReturn(Collections.singletonList(event));
        mvc.perform(get("/api/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok", equalTo(true)))
                .andExpect(jsonPath("$.response", iterableWithSize(1)))
                .andExpect(jsonPath("$.response[0].eventId",equalTo(EVENT_ID)))
                .andExpect(jsonPath("$.response[0].keywords",equalTo(KEYWORDS)))
                .andExpect(jsonPath("$.response[0].hosts[0].name",equalTo(HOST_NAME)));

    }

}
