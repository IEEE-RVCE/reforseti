package org.ieeervce.api.siterearnouveau.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.SoftAssertions;
import org.hamcrest.Matchers;
import org.ieeervce.api.siterearnouveau.entity.Image;
import org.ieeervce.api.siterearnouveau.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ImagesControllerTest {
    private static final int IMAGE_ID = 1;

    private static final byte[] IMAGE_BYTES_ARRAY = new byte[]{2, 3, 65};

    private static final int EVENT_CATEGORY = 4;

    private static final String ALT_TEXT = "altText";
    public static final String IMAGE_PARAM_NAME = "image";
    private static final int IMAGE_CATEGORY_ID = 10;
    public static final String IMAGE_ALT_TEXT = "This is an alt text";

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

    @Captor
    ArgumentCaptor<Image> createdImageCaptor;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(imagesController)
                .build();
        image.setImageId(IMAGE_ID);
        image.setImageBytes(IMAGE_BYTES_ARRAY);
        image.setEventCategory(EVENT_CATEGORY);
        image.setAltText(ALT_TEXT);
    }

    @Test
    void testListAllEmpty() throws Exception {
        when(imageService.list()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/image"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.ok", equalTo(true)))
                .andExpect(jsonPath("$.response", iterableWithSize(0)));
    }

    @Test
    void testListAll() throws Exception {
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
    void testListByCategory() throws Exception {
        int imageCategory = 1;
        when(imageService.listByCategory(imageCategory)).thenReturn(Collections.singletonList(image));
        mockMvc.perform(get("/api/image/category/{imageCategory}", imageCategory))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.ok", equalTo(true)))
                .andExpect(jsonPath("$.response", iterableWithSize(1)))
                .andExpect(jsonPath("$.response[0].imageId", equalTo(IMAGE_ID)))
                .andExpect(jsonPath("$.response[0].eventCategory", equalTo(EVENT_CATEGORY)))
                .andExpect(jsonPath("$.response[0].altText", equalTo(ALT_TEXT)));
    }

    @Test
    void testGetImageBytes() throws Exception {
        when(imageService.getBytesByImageId(IMAGE_ID)).thenReturn(Optional.of(IMAGE_BYTES_ARRAY));

        mockMvc.perform(get("/api/image/{imageId}", IMAGE_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(MockMvcResultMatchers.content().bytes(IMAGE_BYTES_ARRAY));
    }

    @Test
    void testGetImageBytesNotFound() throws Exception {
        when(imageService.getBytesByImageId(IMAGE_ID)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/image/{imageId}", IMAGE_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteImage() throws Exception {
        mockMvc.perform(delete("/api/image/{imageId}", IMAGE_ID))
                .andExpect(status().isOk());
        verify(imageService).delete(IMAGE_ID);
    }

    @Test
    void testCreateImage() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(IMAGE_PARAM_NAME, IMAGE_BYTES_ARRAY);
        Image expectedResponseImage = new Image();
        expectedResponseImage.setImageId(IMAGE_ID);
        expectedResponseImage.setImageBytes(IMAGE_BYTES_ARRAY);
        expectedResponseImage.setAltText(IMAGE_ALT_TEXT);
        expectedResponseImage.setEventCategory(IMAGE_CATEGORY_ID);
        when(imageService.createOrUpdate(createdImageCaptor.capture())).thenReturn(expectedResponseImage);

        mockMvc.perform(
                        multipart("/api/image")
                                .file(mockMultipartFile)
                                .param("eventCategory", String.valueOf(IMAGE_CATEGORY_ID))
                                .param("altText", IMAGE_ALT_TEXT)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.imageId", Matchers.equalTo(IMAGE_ID)))
                .andExpect(jsonPath("$.response.altText",Matchers.equalTo(IMAGE_ALT_TEXT)))
                .andExpect(jsonPath("$.response.eventCategory",Matchers.equalTo(IMAGE_CATEGORY_ID)));

        Image capturedImage = createdImageCaptor.getValue();

        // verify that we're passing the correct value to service
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(capturedImage).isNotNull();
        softAssertions.assertThat(capturedImage).extracting(Image::getImageId).isNull();
        softAssertions.assertThat(capturedImage).extracting(Image::getImageBytes).isEqualTo(IMAGE_BYTES_ARRAY);
        softAssertions.assertThat(capturedImage).extracting(Image::getAltText).isEqualTo(IMAGE_ALT_TEXT);
        softAssertions.assertThat(capturedImage).extracting(Image::getEventCategory).isEqualTo(IMAGE_CATEGORY_ID);
        softAssertions.assertAll();
    }

    @Test
    void testUpdateImage() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(IMAGE_PARAM_NAME, IMAGE_BYTES_ARRAY);
        Image expectedResponseImage = new Image();
        expectedResponseImage.setImageId(IMAGE_ID);
        expectedResponseImage.setImageBytes(IMAGE_BYTES_ARRAY);
        expectedResponseImage.setAltText(IMAGE_ALT_TEXT);
        expectedResponseImage.setEventCategory(IMAGE_CATEGORY_ID);
        when(imageService.createOrUpdate(createdImageCaptor.capture())).thenReturn(expectedResponseImage);

        mockMvc.perform(
                        multipart("/api/image/{imageId}",IMAGE_ID)
                                .file(mockMultipartFile)
                                .param("eventCategory", String.valueOf(IMAGE_CATEGORY_ID))
                                .param("altText", IMAGE_ALT_TEXT)
                                .with(setRequestMethod(HttpMethod.PUT))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.imageId", Matchers.equalTo(IMAGE_ID)))
                .andExpect(jsonPath("$.response.altText",Matchers.equalTo(IMAGE_ALT_TEXT)))
                .andExpect(jsonPath("$.response.eventCategory",Matchers.equalTo(IMAGE_CATEGORY_ID)));

        Image capturedImage = createdImageCaptor.getValue();

        // verify that we're passing the correct value to service
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(capturedImage).isNotNull();
        softAssertions.assertThat(capturedImage).extracting(Image::getImageId).isEqualTo(IMAGE_ID);
        softAssertions.assertThat(capturedImage).extracting(Image::getImageBytes).isEqualTo(IMAGE_BYTES_ARRAY);
        softAssertions.assertThat(capturedImage).extracting(Image::getAltText).isEqualTo(IMAGE_ALT_TEXT);
        softAssertions.assertThat(capturedImage).extracting(Image::getEventCategory).isEqualTo(IMAGE_CATEGORY_ID);
        softAssertions.assertAll();
    }

    /**
     * Set the request method of the mockmvc request being made to the param.
     * @param httpMethod Http Method to use for request
     * @return Request Post processor
     */
    private static RequestPostProcessor setRequestMethod(HttpMethod httpMethod) {
        return request -> {
            request.setMethod(httpMethod.name());
            return request;
        };
    }
}
