package org.ieeervce.api.siterearnouveau.service;

import org.ieeervce.api.siterearnouveau.entity.Event;
import org.ieeervce.api.siterearnouveau.repository.EventsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EventsService {
    private final EventsRepository eventsRepository;

    public EventsService(EventsRepository eventsRepository) {
        this.eventsRepository = eventsRepository;
    }

    public Page<Event> list(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("eventStartTime").ascending());
        return eventsRepository.findAll(pageable);
    }
}
