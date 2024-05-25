package org.ieeervce.api.siterearnouveau.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;

import org.ieeervce.api.siterearnouveau.entity.Event;
import org.ieeervce.api.siterearnouveau.repository.EventsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EventsServiceTest {
    @Mock
    EventsRepository eventsRepository;
    @InjectMocks
    EventsService eventsService;

    @Mock
    Event event;

    @Mock
    Event event2;

    @Test
    void testList(){
        when(eventsRepository.findAll()).thenReturn(Collections.singletonList(event));
        var eventsList = eventsService.list();
        assertThat(eventsList).isNotNull().singleElement().isSameAs(event);
    }

    @Test
    void testListByCategory(){
        int categoryId = 1;
        when(eventsRepository.findByEventCategory(categoryId)).thenReturn(Arrays.asList(event,event2));

        var eventsList = eventsService.listByCategory(categoryId);
        assertThat(eventsList)
                .isNotNull()
                .hasSize(2)
                .containsExactly(event,event2);
    }
}
