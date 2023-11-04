package org.ieeervce.api.siterearnouveau.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.ieeervce.api.siterearnouveau.service.ExecomMembersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith({ MockitoExtension.class, RestDocumentationExtension.class })
class ExecomMembersControllerTest {

    @Mock
    ExecomMembersService execomMembersService;
    @InjectMocks
    ExecomMembersController execomMembersController;

    static ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(execomMembersController)
                .build();
    }

    @Test
    void testList() throws Exception {
        mockMvc.perform(get("/api/execom")).andExpect(status().isOk());
    }
}
