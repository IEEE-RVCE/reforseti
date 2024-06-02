package org.ieeervce.api.siterearnouveau.service;

import org.ieeervce.api.siterearnouveau.entity.Society;
import org.ieeervce.api.siterearnouveau.repository.SocietiesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SocietyServiceTest {
    public static final short ACTUAL_SOCIETY_ID = 1;
    @Mock
    SocietiesRepository societiesRepository;
    @Mock
    Society society;
    @Mock
    Society society2;
    @InjectMocks
    SocietyService societyService;

    @Test
    void list() {
        when(societiesRepository.findAll()).thenReturn(Arrays.asList(society,society2));

        var societyList = societyService.list();
        assertThat(societyList)
                .isNotNull()
                .containsExactly(society,society2);
    }

    @Test
    void getByActualSocietyId() {
        when(societiesRepository.findByReferenceId(ACTUAL_SOCIETY_ID)).thenReturn(Optional.of(society));
        var societyReturned = societyService.getByActualSocietyId(ACTUAL_SOCIETY_ID);
        assertThat(societyReturned)
                .isNotNull()
                .contains(society);
    }

    @Test
    void createOrUpdate() {
        when(societiesRepository.save(society)).thenReturn(society2);
        var createdSociety = societyService.createOrUpdate(society);
        assertThat(createdSociety)
                .isNotNull()
                .isSameAs(society2);
    }

    @Test
    void deleteSociety() {
        societyService.deleteSociety(ACTUAL_SOCIETY_ID);
        verify(societiesRepository).deleteByReferenceId(ACTUAL_SOCIETY_ID);
    }
}