package org.ieeervce.api.siterearnouveau.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ieeervce.api.siterearnouveau.dto.event.EventDTO;
import org.ieeervce.api.siterearnouveau.entity.Event;
import org.ieeervce.api.siterearnouveau.service.EventsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class EventsControllerTest {
    private static final String KEYWORDS = "a b c d";

    private static final int EVENT_ID = 1;
    private static final String HOST_NAME = "abcd efgh";
    private static final int CATEGORY_ID = 20;
    public static final String EVENT_NAME = "Event name";
    public static final int EVENT_CATEGORY = 10;
    public static final String HOST_PICTURE_LINK = "Picture link";
    public static final String HOST_DETAILS = "Big details";

    List<Event.Host> exampleHostsList = Collections.singletonList(new Event.Host());

    @Mock
    EventsService eventsService;
    @Spy
    ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    EventsController eventsController;

    @Spy
    ObjectMapper objectMapper = new ObjectMapper();
    @Spy
    Event event;

    MockMvc mvc;

    @BeforeEach
    void setup() {
        objectMapper.findAndRegisterModules();
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

    @Test
    void testListByCategory() throws Exception {
        when(eventsService.listByCategory(CATEGORY_ID)).thenReturn(Collections.singletonList(event));
        mvc.perform(get("/api/event/category/{categoryId}",CATEGORY_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok", equalTo(true)))
                .andExpect(jsonPath("$.response", iterableWithSize(1)))
                .andExpect(jsonPath("$.response[0].eventId",equalTo(EVENT_ID)))
                .andExpect(jsonPath("$.response[0].keywords",equalTo(KEYWORDS)))
                .andExpect(jsonPath("$.response[0].hosts[0].name",equalTo(HOST_NAME)));

    }

    @Test
    void testGet() throws Exception {
        when(eventsService.getById(EVENT_ID)).thenReturn(Optional.of(event));
        mvc.perform(get("/api/event/{eventId}",EVENT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok", equalTo(true)))
                .andExpect(jsonPath("$.response.eventId",equalTo(EVENT_ID)))
                .andExpect(jsonPath("$.response.keywords",equalTo(KEYWORDS)))
                .andExpect(jsonPath("$.response.hosts[0].name",equalTo(HOST_NAME)));

    }
    @Test
    void testGetNotFound() throws Exception {
        when(eventsService.getById(EVENT_ID)).thenReturn(Optional.empty());
        mvc.perform(get("/api/event/{eventId}",EVENT_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDelete() throws Exception{
        mvc.perform(delete("/api/event/{eventId}",EVENT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok", equalTo(true)))
                .andExpect(jsonPath("$.response", nullValue()))
                .andExpect(jsonPath("$.message",nullValue()));
        verify(eventsService).delete(EVENT_ID);
    }
    @Test
    void testCreate() throws Exception {
        EventDTO newEventDTO = getExpectedResponseEventWithoutId();

        Event requestedEventEntityToSave = modelMapper.map(getExpectedResponseEventWithoutId(),Event.class);
        Event createdData = modelMapper.map(getExpectedResponseEventWithoutId(),Event.class);
        createdData.setEventId(EVENT_ID);

        when(eventsService.createOrUpdate(requestedEventEntityToSave)).thenReturn(createdData);
        String requestContent = objectMapper.writeValueAsString(newEventDTO);
        mvc.perform(post("/api/event").content(requestContent).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok",equalTo(true)))
                .andExpect(jsonPath("$.response.eventId",equalTo(EVENT_ID)))
                .andExpect(jsonPath("$.response.eventName",equalTo(EVENT_NAME)))
                .andExpect(jsonPath("$.response.hosts",iterableWithSize(1)))
                .andExpect(jsonPath("$.response.hosts[0].piclink",equalTo(HOST_PICTURE_LINK)))
                .andExpect(jsonPath("$.response.hosts[0].details",equalTo(HOST_DETAILS)))
                .andExpect(jsonPath("$.response.hosts[0].name",equalTo(HOST_NAME)));
    }

    @Test
    void testUpdate() throws Exception {
        EventDTO newEventDTO = getExpectedResponseEventWithoutId();

        Event requestedEventEntityToSave = modelMapper.map(getExpectedResponseEventWithoutId(),Event.class);
        requestedEventEntityToSave.setEventId(EVENT_ID);
        Event createdData = modelMapper.map(getExpectedResponseEventWithoutId(),Event.class);
        createdData.setEventId(EVENT_ID);

        when(eventsService.createOrUpdate(requestedEventEntityToSave)).thenReturn(createdData);
        String requestContent = objectMapper.writeValueAsString(newEventDTO);
        mvc.perform(put("/api/event/{eventId}",EVENT_ID).content(requestContent).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok",equalTo(true)))
                .andExpect(jsonPath("$.response.eventId",equalTo(EVENT_ID)))
                .andExpect(jsonPath("$.response.eventName",equalTo(EVENT_NAME)))
                .andExpect(jsonPath("$.response.hosts",iterableWithSize(1)))
                .andExpect(jsonPath("$.response.hosts[0].piclink",equalTo(HOST_PICTURE_LINK)))
                .andExpect(jsonPath("$.response.hosts[0].details",equalTo(HOST_DETAILS)))
                .andExpect(jsonPath("$.response.hosts[0].name",equalTo(HOST_NAME)));
    }

    private EventDTO getExpectedResponseEventWithoutId() {

        EventDTO result = new EventDTO();
        result.setEventName(EVENT_NAME);
        result.setEventCategory(EVENT_CATEGORY);
        result.setEventStartTime(LocalDateTime.MIN);
        result.setEventEndTime(LocalDateTime.MAX);
        result.setKeywords(KEYWORDS);
        result.setHosts(Collections.singletonList(getHost()));
        return result;
    }

    private Event.Host getHost() {
        Event.Host host = new Event.Host();
        host.setName(HOST_NAME);
        host.setPiclink(HOST_PICTURE_LINK);
        host.setDetails(HOST_DETAILS);
        return host;
    }

}
