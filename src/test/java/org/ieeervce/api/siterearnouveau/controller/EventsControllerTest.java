package org.ieeervce.api.siterearnouveau.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class EventsControllerTest {
    private static final String KEYWORDS = "a b c d";

    private static final int    EVENT_ID  = 1;
    private static final String HOST_NAME = "abcd efgh";

    private static final String EXAMPLE_HOSTS_TREE_STRING = String.format("{\"name\":\"%s\"}", HOST_NAME);

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
        mvc = MockMvcBuilders.standaloneSetup(eventsController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
        event.setEventId(EVENT_ID);
        event.setKeywords(KEYWORDS);
        event.setHosts(objectMapper.readTree(EXAMPLE_HOSTS_TREE_STRING));
    }

    @Test
    void testList() throws Exception {

        Page<Event> mockEventPage = new PageImpl<>(Collections.singletonList(event));
        Pageable pageable = Pageable.ofSize(20).withPage(0);
        when(eventsService.list(pageable)).thenReturn(mockEventPage);
        mvc.perform(get("/api/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok", equalTo(true)))
                .andExpect(jsonPath("$.response.content", iterableWithSize(1)))
                .andExpect(jsonPath("$.response.content[0].eventId", equalTo(EVENT_ID)))
                .andExpect(jsonPath("$.response.content[0].keywords", equalTo(KEYWORDS)))
                .andExpect(jsonPath("$.response.content[0].hosts.name", equalTo(HOST_NAME)));
    }

}
