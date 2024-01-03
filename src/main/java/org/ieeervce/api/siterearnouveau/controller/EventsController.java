package org.ieeervce.api.siterearnouveau.controller;

import java.util.List;

import org.ieeervce.api.siterearnouveau.dto.ResultsDTO;
import org.ieeervce.api.siterearnouveau.entity.Event;
import org.ieeervce.api.siterearnouveau.service.EventsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/event")
public class EventsController {
    private EventsService eventsService;

    public EventsController(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @GetMapping
    public ResultsDTO<List<Event>> list() {
        List<Event> events = eventsService.list();
        return new ResultsDTO<>(events);
    }
}
