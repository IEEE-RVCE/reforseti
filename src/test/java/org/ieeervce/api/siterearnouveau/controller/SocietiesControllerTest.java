package org.ieeervce.api.siterearnouveau.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ieeervce.api.siterearnouveau.dto.society.SocietyDTO;
import org.ieeervce.api.siterearnouveau.entity.Society;
import org.ieeervce.api.siterearnouveau.service.SocietyService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = SocietiesController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class SocietiesControllerTest {
    public static final Integer SOCIETY_GENERATED_ID_1 = 1;
    private static final Integer SOCIETY_GENERATED_ID_2 = 2;
    private static final short SOCIETY_REFERENCE_ID_1 = 10;
    private static final short SOCIETY_REFERENCE_ID_2 = 11;
    public static final String SOCIETY_NAME = "My Society";
    public static final String SOCIETY_MISSION = "Big mission";
    public static final String SOCIETY_VISION = "10/10";
    public static final String SOCIETY_DESCRIPTION = "Lots of description";
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    SocietyService societyService;
    @SpyBean
    ModelMapper modelMapper;



    @Test
    void list() throws Exception {
        Society society1 = createTestSociety(SOCIETY_GENERATED_ID_1, SOCIETY_REFERENCE_ID_1, false);
        Society society2 = createTestSociety(SOCIETY_GENERATED_ID_2, SOCIETY_REFERENCE_ID_2, true);
        when(societyService.list()).thenReturn(Arrays.asList(society1, society2));
        mockMvc.perform(get("/api/society"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok", equalTo(true)))
                .andExpect(jsonPath("$.message", nullValue()))
                .andExpect(jsonPath("$.response", iterableWithSize(2)))
                .andExpect(jsonPath("$.response[0].generatedId", equalTo(SOCIETY_GENERATED_ID_1)))
                .andExpect(jsonPath("$.response[0].societyName", equalTo(SOCIETY_NAME)))
                .andExpect(jsonPath("$.response[0].vision", equalTo(SOCIETY_VISION)))
                .andExpect(jsonPath("$.response[0].mission", equalTo(SOCIETY_MISSION)))
                .andExpect(jsonPath("$.response[0].descriptionText", equalTo(SOCIETY_DESCRIPTION)))
                .andExpect(jsonPath("$.response[0].referenceId", equalTo(Short.toUnsignedInt( SOCIETY_REFERENCE_ID_1))))
                .andExpect(jsonPath("$.response[0].affinity", equalTo(false)))
                .andExpect(jsonPath("$.response[1].referenceId", equalTo(Short.toUnsignedInt(SOCIETY_REFERENCE_ID_2))))
                .andExpect(jsonPath("$.response[1].affinity", equalTo(true)));
    }

    @Test
    void getBySocietyIdFound() throws Exception {
        Society society = createTestSociety(SOCIETY_GENERATED_ID_1, SOCIETY_REFERENCE_ID_1, false);
        when(societyService.getByActualSocietyId(SOCIETY_REFERENCE_ID_1)).thenReturn(Optional.of(society));
        mockMvc.perform(get("/api/society/{sid}",SOCIETY_REFERENCE_ID_1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok", equalTo(true)))
                .andExpect(jsonPath("$.message", nullValue()))
                .andExpect(jsonPath("$.response.generatedId", equalTo(SOCIETY_GENERATED_ID_1)))
                .andExpect(jsonPath("$.response.societyName", equalTo(SOCIETY_NAME)))
                .andExpect(jsonPath("$.response.vision", equalTo(SOCIETY_VISION)))
                .andExpect(jsonPath("$.response.mission", equalTo(SOCIETY_MISSION)))
                .andExpect(jsonPath("$.response.descriptionText", equalTo(SOCIETY_DESCRIPTION)))
                .andExpect(jsonPath("$.response.referenceId", equalTo(Short.toUnsignedInt( SOCIETY_REFERENCE_ID_1))))
                .andExpect(jsonPath("$.response.affinity", equalTo(false)));
    }
    @Test
    void getBySocietyIdNotFound() throws Exception {
        when(societyService.getByActualSocietyId(SOCIETY_REFERENCE_ID_1)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/society/{sid}",SOCIETY_REFERENCE_ID_1))
                .andExpect(status().isNotFound());
    }

    @Test
    void create() throws Exception {
        Society societyPendingSave = createTestSociety(null, SOCIETY_REFERENCE_ID_1, false);
        Society societySaved = createTestSociety(SOCIETY_GENERATED_ID_1, SOCIETY_REFERENCE_ID_1, false);
        SocietyDTO societyDTO = modelMapper.map(societyPendingSave,SocietyDTO.class);

        String societyDTOJsonString = objectMapper.writeValueAsString(societyDTO);
        when(societyService.createOrUpdate(any())).thenReturn(societySaved);
        mockMvc.perform(
                post("/api/society")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(societyDTOJsonString)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok", equalTo(true)))
                .andExpect(jsonPath("$.message", nullValue()))
                .andExpect(jsonPath("$.response.generatedId", equalTo(SOCIETY_GENERATED_ID_1)))
                .andExpect(jsonPath("$.response.societyName", equalTo(SOCIETY_NAME)))
                .andExpect(jsonPath("$.response.vision", equalTo(SOCIETY_VISION)))
                .andExpect(jsonPath("$.response.mission", equalTo(SOCIETY_MISSION)))
                .andExpect(jsonPath("$.response.descriptionText", equalTo(SOCIETY_DESCRIPTION)))
                .andExpect(jsonPath("$.response.referenceId", equalTo(Short.toUnsignedInt( SOCIETY_REFERENCE_ID_1))))
                .andExpect(jsonPath("$.response.affinity", equalTo(false)));
    }

    @Test
    void update() throws Exception {
        Society societyPendingSave = createTestSociety(null, SOCIETY_REFERENCE_ID_1, false);
        Society societySaved = createTestSociety(SOCIETY_GENERATED_ID_1, SOCIETY_REFERENCE_ID_1, false);
        SocietyDTO societyDTO = modelMapper.map(societyPendingSave,SocietyDTO.class);
        String societyDTOJsonString = objectMapper.writeValueAsString(societyDTO);
        when(societyService.getByActualSocietyId(anyShort())).thenReturn(Optional.of(societySaved));
        when(societyService.createOrUpdate(any())).thenReturn(societySaved);
        mockMvc.perform(
                        put("/api/society/{sid}", SOCIETY_REFERENCE_ID_1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(societyDTOJsonString)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok", equalTo(true)))
                .andExpect(jsonPath("$.message", nullValue()))
                .andExpect(jsonPath("$.response.generatedId", equalTo(SOCIETY_GENERATED_ID_1)))
                .andExpect(jsonPath("$.response.societyName", equalTo(SOCIETY_NAME)))
                .andExpect(jsonPath("$.response.vision", equalTo(SOCIETY_VISION)))
                .andExpect(jsonPath("$.response.mission", equalTo(SOCIETY_MISSION)))
                .andExpect(jsonPath("$.response.descriptionText", equalTo(SOCIETY_DESCRIPTION)))
                .andExpect(jsonPath("$.response.referenceId", equalTo(Short.toUnsignedInt( SOCIETY_REFERENCE_ID_1))))
                .andExpect(jsonPath("$.response.affinity", equalTo(false)));
        // once for mock, once for update
        verify(societySaved, times(2)).setSocietyName(any());
        verify(societySaved, times(2)).setVision(any());
        verify(societySaved, times(2)).setMission(any());
        verify(societySaved, times(2)).setDescriptionText(any());
        verify(societySaved, times(2)).setReferenceId(anyShort());
        verify(societySaved, times(2)).setAffinity(false);
        // once for mock only
        verify(societySaved).setGeneratedId(any());
    }

    @Test
    void updateNotFound() throws Exception {
        Society societyPendingSave = createTestSociety(null, SOCIETY_REFERENCE_ID_1, false);
        Society societySaved = createTestSociety(SOCIETY_GENERATED_ID_1, SOCIETY_REFERENCE_ID_1, false);
        SocietyDTO societyDTO = modelMapper.map(societyPendingSave,SocietyDTO.class);
        String societyDTOJsonString = objectMapper.writeValueAsString(societyDTO);
        when(societyService.createOrUpdate(any())).thenReturn(societySaved);
        mockMvc.perform(
                        put("/api/society/{sid}", SOCIETY_REFERENCE_ID_1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(societyDTOJsonString)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/society/{sid}",SOCIETY_REFERENCE_ID_1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message",nullValue()))
                .andExpect(jsonPath("$.ok", is(true)));
        verify(societyService).deleteSociety(SOCIETY_REFERENCE_ID_1);

    }

    private Society createTestSociety(Integer societyGeneratedId, short societyActualId, boolean societyIsAffinity) {
        Society society = spy(new Society());
        if (societyGeneratedId != null) {
            society.setGeneratedId(societyGeneratedId);
        }
        society.setSocietyName(SOCIETY_NAME);
        society.setReferenceId(societyActualId);
        society.setAffinity(societyIsAffinity);
        society.setMission(SOCIETY_MISSION);
        society.setVision(SOCIETY_VISION);
        society.setDescriptionText(SOCIETY_DESCRIPTION);

        return society;
    }
}