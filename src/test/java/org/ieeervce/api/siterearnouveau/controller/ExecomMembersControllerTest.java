package org.ieeervce.api.siterearnouveau.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.hamcrest.Matchers;
import org.ieeervce.api.siterearnouveau.dto.execom.ExecomMemberDTO;
import org.ieeervce.api.siterearnouveau.entity.ExecomMember;
import org.ieeervce.api.siterearnouveau.service.ExecomMembersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class ExecomMembersControllerTest {

    private static final LocalDate NOW_TIME = LocalDate.now();

    private static final int SOCIETY_ID = 2;

    private static final int MEMBER_ID = 1;

    private static final String POSITION = "big";

    private static final String IMAGE_LINK = "imageLink";

    private static final String LASTNAME = "Doe";

    private static final String FIRSTNAME = "John";

    @Mock
    ExecomMembersService execomMembersService;

    @Spy
    ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    @Spy
    ModelMapper modelMapper;

    @InjectMocks
    ExecomMembersController execomMembersController;

    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(execomMembersController)
                .build();
    }

    @Test
    void testFindAllEmpty() throws Exception {
        mockMvc.perform(get("/api/execom/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok", equalTo(true)));
    }

    @Test
    void testFindAll() throws Exception {
        ExecomMember execomMember = new ExecomMember();
        execomMember.setFirstName(FIRSTNAME);
        execomMember.setLastName(LASTNAME);
        execomMember.setImagePath(IMAGE_LINK);
        execomMember.setPosition(POSITION);
        execomMember.setId(MEMBER_ID);
        execomMember.setSocietyId(SOCIETY_ID);
        execomMember.setTenureStartDate(NOW_TIME);
        execomMember.setTenureEndDate(NOW_TIME);

        List<ExecomMember> execomMembers = Collections.singletonList(execomMember);
        when(execomMembersService.findAll()).thenReturn(execomMembers);
        mockMvc.perform(get("/api/execom/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok", equalTo(true)))
                .andExpect(jsonPath("$.response", Matchers.iterableWithSize(1)))
                .andExpect(jsonPath("$.response[0].id", equalTo(MEMBER_ID)));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/execom/{execomId}", MEMBER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok", equalTo(true)));
        verify(execomMembersService).deleteByMemberId(MEMBER_ID);
    }

    @Test
    void testEmptyAlumni() throws Exception {
        mockMvc.perform(get("/api/execom/alumni/{societyId}", SOCIETY_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok", equalTo(true)));
        verify(execomMembersService).findAlumniBySocietyId(SOCIETY_ID);
    }

    @Test
    void testEndTenureForSociety() throws Exception {
        ExecomMember execomMember = new ExecomMember();
        execomMember.setFirstName(FIRSTNAME);
        execomMember.setLastName(LASTNAME);
        execomMember.setImagePath(IMAGE_LINK);
        execomMember.setPosition(POSITION);
        execomMember.setId(MEMBER_ID);
        execomMember.setSocietyId(SOCIETY_ID);
        execomMember.setTenureStartDate(NOW_TIME);
        execomMember.setTenureEndDate(NOW_TIME);

        when(execomMembersService.endTenureForSocietyId(SOCIETY_ID))
                .thenReturn(Collections.singletonList(execomMember));
        mockMvc.perform(post("/api/execom/end/{societyId}", SOCIETY_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok", equalTo(true)))
                .andExpect(jsonPath("$.response", Matchers.iterableWithSize(1)))
                .andExpect(jsonPath("$.response[0].id", equalTo(MEMBER_ID)))
                .andExpect(jsonPath("$.response[0].tenureEndDate", Matchers.notNullValue()));
    }

    @Test
    void testListCurrentBySocietyId() throws Exception {
        ExecomMember execomMember = new ExecomMember();
        execomMember.setFirstName(FIRSTNAME);
        execomMember.setLastName(LASTNAME);
        execomMember.setImagePath(IMAGE_LINK);
        execomMember.setPosition(POSITION);
        execomMember.setId(MEMBER_ID);
        execomMember.setSocietyId(SOCIETY_ID);
        execomMember.setTenureStartDate(NOW_TIME);
        execomMember.setTenureEndDate(NOW_TIME);

        when(execomMembersService.findCurrentBySocietyId(SOCIETY_ID))
                .thenReturn(Collections.singletonList(execomMember));
        mockMvc.perform(get("/api/execom/{societyId}", SOCIETY_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok", equalTo(true)))
                .andExpect(jsonPath("$.response", Matchers.iterableWithSize(1)))
                .andExpect(jsonPath("$.response[0].id", equalTo(MEMBER_ID)))
                .andExpect(jsonPath("$.response[0].tenureEndDate", Matchers.notNullValue()));
    }

    @Test
    void testCreate() throws Exception {
        ExecomMemberDTO execomMemberDto = new ExecomMemberDTO();
        execomMemberDto.setFirstName(FIRSTNAME);
        execomMemberDto.setLastName(LASTNAME);
        execomMemberDto.setImagePath(IMAGE_LINK);
        execomMemberDto.setPosition(POSITION);
        execomMemberDto.setSocietyId(SOCIETY_ID);
        execomMemberDto.setTenureStartDate(NOW_TIME);
        execomMemberDto.setTenureEndDate(NOW_TIME);
        String execomMemberJson = objectMapper.writeValueAsString(execomMemberDto);
        ExecomMember execomMember = modelMapper.map(execomMemberDto, ExecomMember.class);
        execomMember.setId(MEMBER_ID);

        when(execomMembersService.create(any())).thenReturn(execomMember);
        mockMvc.perform(post("/api/execom").contentType(MediaType.APPLICATION_JSON).content(execomMemberJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok", equalTo(true)))
                .andExpect(jsonPath("$.response", Matchers.notNullValue()))
                .andExpect(jsonPath("$.response.id", equalTo(MEMBER_ID)))
                .andExpect(jsonPath("$.response.tenureEndDate", Matchers.notNullValue()));
    }

    @Test
    void testUpdate() throws Exception {
        ExecomMemberDTO execomMemberDto = new ExecomMemberDTO();
        execomMemberDto.setFirstName(FIRSTNAME);
        execomMemberDto.setLastName(LASTNAME);
        execomMemberDto.setImagePath(IMAGE_LINK);
        execomMemberDto.setPosition(POSITION);
        execomMemberDto.setSocietyId(SOCIETY_ID);
        execomMemberDto.setTenureStartDate(NOW_TIME);
        execomMemberDto.setTenureEndDate(NOW_TIME);
        String execomMemberJson = objectMapper.writeValueAsString(execomMemberDto);
        ExecomMember execomMember = modelMapper.map(execomMemberDto, ExecomMember.class);
        execomMember.setId(MEMBER_ID);

        when(execomMembersService.updateExecomMember(eq(MEMBER_ID), any())).thenReturn(execomMember);
        mockMvc.perform(put("/api/execom/{memberId}", MEMBER_ID).contentType(MediaType.APPLICATION_JSON)
                .content(execomMemberJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok", equalTo(true)))
                .andExpect(jsonPath("$.response", Matchers.notNullValue()))
                .andExpect(jsonPath("$.response.id", equalTo(MEMBER_ID)))
                .andExpect(jsonPath("$.response.tenureEndDate", Matchers.notNullValue()));
    }

    @Test
    void testEndTenureForAll() throws Exception {
        ExecomMember execomMember = new ExecomMember();
        execomMember.setFirstName(FIRSTNAME);
        execomMember.setLastName(LASTNAME);
        execomMember.setImagePath(IMAGE_LINK);
        execomMember.setPosition(POSITION);
        execomMember.setId(MEMBER_ID);
        execomMember.setSocietyId(SOCIETY_ID);
        execomMember.setTenureStartDate(NOW_TIME);
        execomMember.setTenureEndDate(NOW_TIME);

        when(execomMembersService.endTenureForAllCurrent()).thenReturn(Collections.singletonList(execomMember));
        mockMvc.perform(post("/api/execom/end"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok", equalTo(true)))
                .andExpect(jsonPath("$.response", Matchers.iterableWithSize(1)))
                .andExpect(jsonPath("$.response[0].id", equalTo(MEMBER_ID)))
                .andExpect(jsonPath("$.response[0].tenureEndDate", Matchers.notNullValue()));
    }

    @Test
    void testAlumni() throws Exception {
        ExecomMember execomMember = new ExecomMember();
        execomMember.setFirstName(FIRSTNAME);
        execomMember.setLastName(LASTNAME);
        execomMember.setImagePath(IMAGE_LINK);
        execomMember.setPosition(POSITION);
        execomMember.setId(MEMBER_ID);
        execomMember.setSocietyId(SOCIETY_ID);
        execomMember.setTenureStartDate(NOW_TIME);
        execomMember.setTenureEndDate(NOW_TIME);

        ExecomMember execomMember2 = new ExecomMember();
        execomMember2.setFirstName(FIRSTNAME);
        execomMember2.setLastName(LASTNAME);
        execomMember2.setImagePath(IMAGE_LINK);
        execomMember2.setPosition(POSITION);
        execomMember2.setId(MEMBER_ID);
        execomMember2.setSocietyId(SOCIETY_ID);
        execomMember2.setTenureStartDate(NOW_TIME);
        execomMember2.setTenureEndDate(NOW_TIME);

        List<ExecomMember> execomMembers = Arrays.asList(execomMember, execomMember2);
        when(execomMembersService.findAlumniBySocietyId(SOCIETY_ID)).thenReturn(execomMembers);
        String year = String.valueOf(NOW_TIME.getYear());
        String yearMatcher = "$.response." + year;
        mockMvc.perform(get("/api/execom/alumni/{societyId}", SOCIETY_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok", equalTo(true)))
                .andExpect(jsonPath("$.response", Matchers.aMapWithSize(1)))
                .andExpect(jsonPath("$.response", Matchers.hasKey(equalTo(year))))
                .andExpect(jsonPath(yearMatcher, Matchers.iterableWithSize(2)));
    }
}
