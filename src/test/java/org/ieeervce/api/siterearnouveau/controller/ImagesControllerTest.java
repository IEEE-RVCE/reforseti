package org.ieeervce.api.siterearnouveau.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Collections;
import java.util.Optional;

import org.ieeervce.api.siterearnouveau.entity.Image;
import org.ieeervce.api.siterearnouveau.service.ImageService;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class ImagesControllerTest {
    private static final int IMAGE_ID = 1;

    private static final byte[] IMAGE_BYTES = new byte[]{2,3};

    private static final int EVENT_CATEGORY = 4;

    private static final String ALT_TEXT = "altText";

    @Mock
    ImageService imageService;

    @Spy
    ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    @Spy
    ModelMapper modelMapper;

    @InjectMocks
    ImagesController imagesController;

    MockMvc mockMvc;

    @Spy
    Image image;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(imagesController)
                .build();
        image.setImageId(IMAGE_ID);
        image.setImageBytes(IMAGE_BYTES);
        image.setEventCategory(EVENT_CATEGORY);
        image.setAltText(ALT_TEXT);
    }

    @Test
    void testListAllEmpty() throws Exception {
        when(imageService.list()).thenReturn(Collections.singletonList(image));
        mockMvc.perform(get("/api/image"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.ok", equalTo(true)))
                .andExpect(jsonPath("$.response", iterableWithSize(1)))
                .andExpect(jsonPath("$.response[0].imageId", equalTo(IMAGE_ID)))
                .andExpect(jsonPath("$.response[0].eventCategory", equalTo(EVENT_CATEGORY)))
                .andExpect(jsonPath("$.response[0].altText", equalTo(ALT_TEXT)));
    }
    @Test
    void testGetImageBytes() throws Exception {
        when(imageService.getBytesByImageId(IMAGE_ID)).thenReturn(Optional.of(IMAGE_BYTES));
        
        mockMvc.perform(get("/api/image/{imageId}",IMAGE_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(MockMvcResultMatchers.content().bytes(IMAGE_BYTES));
    }
}
