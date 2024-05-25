package org.ieeervce.api.siterearnouveau.service;

import java.util.List;
import java.util.Optional;

import org.ieeervce.api.siterearnouveau.entity.Event;
import org.ieeervce.api.siterearnouveau.repository.EventsRepository;
import org.springframework.stereotype.Service;

@Service
public class EventsService {
    private final EventsRepository eventsRepository;

    public EventsService(EventsRepository eventsRepository) {
        this.eventsRepository = eventsRepository;
    }

    public List<Event> list() {
        return eventsRepository.findAll();
    }

    public List<Event> listByCategory(int categoryId){
        return eventsRepository.findByEventCategory(categoryId);
    }

    public Optional<Event> getById(int eventId){
        return eventsRepository.findById(eventId);
    }

    public void delete(int eventId){
        eventsRepository.deleteById(eventId);
    }

    public Event createOrUpdate(Event event) {
        return eventsRepository.save(event);
    }

}
