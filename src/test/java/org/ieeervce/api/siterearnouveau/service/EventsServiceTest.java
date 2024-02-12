package org.ieeervce.api.siterearnouveau.service;

import org.ieeervce.api.siterearnouveau.entity.Event;
import org.ieeervce.api.siterearnouveau.repository.EventsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventsServiceTest {
    @Mock
    EventsRepository eventsRepository;
    @InjectMocks
    EventsService    eventsService;

    @Mock
    Event event;

    @Test
    void testList() {
        Pageable pageable = Pageable.ofSize(20).withPage(0);
        Page<Event> eventPage = new PageImpl<>(Collections.singletonList(event), pageable, 1);
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("eventStartTime").ascending());
        when(eventsRepository.findAll(pageable)).thenReturn(eventPage);
        Page<Event> results = eventsService.list(pageable);
        assertThat(results).isNotNull();
        assertThat(results.getContent()).containsExactly(event);
    }
}
