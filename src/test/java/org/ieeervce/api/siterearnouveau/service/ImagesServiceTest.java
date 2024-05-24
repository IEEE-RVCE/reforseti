package org.ieeervce.api.siterearnouveau.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Optional;

import org.ieeervce.api.siterearnouveau.entity.Image;
import org.ieeervce.api.siterearnouveau.repository.ImagesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ImagesServiceTest {
    private static final byte[] IMAGE_BYTES = new byte[]{1, 2};

    private static final Integer IMAGE_ID = 1;

    @Mock
    ImagesRepository imageRepository;

    @InjectMocks
    ImageService imageService;

    @Mock
    Image image;

    @Test
    void testList() {
        when(imageRepository.findAll()).thenReturn(Collections.singletonList(image));
        var imageList = imageService.list();
        assertThat(imageList).isNotNull().isNotEmpty().hasSize(1);
    }

    @Test
    void testListByCategory() {
        int imageCategory = 1;
        when(imageRepository.findByEventCategory(imageCategory)).thenReturn(Collections.singletonList(image));
        var imageList = imageService.listByCategory(1);
        assertThat(imageList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .first()
                .isSameAs(image);
    }

    @Test
    void testGetBytes() {
        when(imageRepository.findById(IMAGE_ID)).thenReturn(Optional.of(image));
        when(image.getImageBytes()).thenReturn(IMAGE_BYTES);
        Optional<byte[]> imageBytesReturned = imageService.getBytesByImageId(IMAGE_ID);

        assertThat(imageBytesReturned).hasValue(IMAGE_BYTES);
    }

    @Test
    void testGetBytesEmpty() {
        when(imageRepository.findById(IMAGE_ID)).thenReturn(Optional.empty());
        Optional<byte[]> imageBytesReturned = imageService.getBytesByImageId(IMAGE_ID);

        assertThat(imageBytesReturned).isEmpty();
    }

    @Test
    void testDelete() {
        imageService.delete(IMAGE_ID);
        verify(imageRepository).deleteById(IMAGE_ID);
    }

    @Test
    void testCreateOrUpdateEmptyImage() {
        assertThatThrownBy(() -> imageService.createOrUpdate(image))
                .isInstanceOf(IllegalArgumentException.class);
        verify(imageRepository, never()).save(image);
    }

    @Test
    void testCreateOrUpdate() {
        Image savedImage = mock(Image.class);
        when(imageRepository.save(image)).thenReturn(savedImage);
        when(image.getImageBytes()).thenReturn(new byte[]{1, 2, 3});
        Image returnedImage = imageService.createOrUpdate(image);

        assertThat(returnedImage)
                .isNotNull()
                .isSameAs(savedImage);
    }
}
