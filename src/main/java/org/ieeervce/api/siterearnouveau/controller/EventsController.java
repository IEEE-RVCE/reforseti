package org.ieeervce.api.siterearnouveau.controller;

import java.util.List;

import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import org.ieeervce.api.siterearnouveau.dto.ResultsDTO;
import org.ieeervce.api.siterearnouveau.dto.event.EventDTO;
import org.ieeervce.api.siterearnouveau.entity.Event;
import org.ieeervce.api.siterearnouveau.exception.DataNotFoundException;
import org.ieeervce.api.siterearnouveau.service.EventsService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/event")
@Timed
public class EventsController {
    private final EventsService eventsService;
    private final ModelMapper modelMapper;

    public EventsController(EventsService eventsService, ModelMapper modelMapper) {
        this.eventsService = eventsService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResultsDTO<List<Event>> list() {
        List<Event> events = eventsService.list();
        return new ResultsDTO<>(events);
    }

    @GetMapping("/category/{categoryId}")
    public ResultsDTO<List<Event>> listByCategory(@PathVariable int categoryId){
        List<Event> events = eventsService.listByCategory(categoryId);
        return new ResultsDTO<>(events);
    }
    @GetMapping("/{eventId}")
    public ResultsDTO<Event> get(@PathVariable int eventId) throws DataNotFoundException {
        Event event = eventsService.getById(eventId).orElseThrow(DataNotFoundException::new);
        return new ResultsDTO<>(event);
    }

    @DeleteMapping("/{eventId}")
    public ResultsDTO<Void> delete(@PathVariable int eventId) {
        eventsService.delete(eventId);
        return new ResultsDTO<>(null);
    }

    @PostMapping
    public ResultsDTO<Event> create(@RequestBody @Valid EventDTO eventDTO){
        Event event = modelMapper.map(eventDTO,Event.class);
        return new ResultsDTO<>(eventsService.createOrUpdate(event));
    }

    @PutMapping("/{eventId}")
    public ResultsDTO<Event> update(@PathVariable int eventId, @RequestBody @Valid EventDTO eventDTO){
        Event event = modelMapper.map(eventDTO,Event.class);
        event.setEventId(eventId);
        return new ResultsDTO<>(eventsService.createOrUpdate(event));
    }

}
