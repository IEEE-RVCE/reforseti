package org.ieeervce.api.siterearnouveau.service;

import static org.mockito.Mockito.when;

import java.util.Collections;

import org.assertj.core.api.Assertions;
import org.ieeervce.api.siterearnouveau.entity.Image;
import org.ieeervce.api.siterearnouveau.repository.ImageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ImagesServiceTest {
    @Mock
    ImageRepository imageRepository;

    @InjectMocks
    ImageService imageService;

    @Mock
    Image image1;


    @Test
    void testList(){
        when(imageRepository.findAll()).thenReturn(Collections.singletonList(image1));
        var imageList = imageService.list();
        Assertions.assertThat(imageList).isNotNull().isNotEmpty().hasSize(1);
    }
}
