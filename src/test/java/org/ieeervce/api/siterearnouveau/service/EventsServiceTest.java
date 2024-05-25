package org.ieeervce.api.siterearnouveau.service;

import org.ieeervce.api.siterearnouveau.entity.Event;
import org.ieeervce.api.siterearnouveau.repository.EventsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventsServiceTest {
    public static final int CATEGORY_ID = 20;
    public static final int EVENT_ID = 1;
    @Mock
    EventsRepository eventsRepository;
    @InjectMocks
    EventsService eventsService;

    @Mock
    Event event;

    @Mock
    Event event2;

    @Test
    void testList() {
        when(eventsRepository.findAll()).thenReturn(Collections.singletonList(event));
        List<Event> eventsList = eventsService.list();
        assertThat(eventsList).isNotNull().singleElement().isSameAs(event);
    }

    @Test
    void testListByCategory() {
        when(eventsRepository.findByEventCategory(CATEGORY_ID)).thenReturn(Arrays.asList(event, event2));

        List<Event> eventsList = eventsService.listByCategory(CATEGORY_ID);
        assertThat(eventsList)
                .isNotNull()
                .hasSize(2)
                .containsExactly(event, event2);
    }

    @Test
    void testGet() {
        when(eventsRepository.findById(EVENT_ID)).thenReturn(Optional.of(event));
        Optional<Event> eventOptional = eventsService.getById(EVENT_ID);
        assertThat(eventOptional).contains(event);
    }

    @Test
    void testDelete() {
        eventsService.delete(EVENT_ID);
        verify(eventsRepository).deleteById(EVENT_ID);
    }

    @Test
    void testCreateOrUpdate() {
        when(eventsRepository.save(event)).thenReturn(event2);
        var eventReturned = eventsService.createOrUpdate(event);
        assertThat(eventReturned)
                .isNotNull()
                .isSameAs(event2);
    }
}
