package org.ieeervce.api.siterearnouveau.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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

    @Test
    void testList(){
        when(eventsRepository.findAll()).thenReturn(Collections.singletonList(event));
        var eventsList = eventsService.list();
        assertThat(eventsList).isNotNull().singleElement().isSameAs(event);
    }
}
