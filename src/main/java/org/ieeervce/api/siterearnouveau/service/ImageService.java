package org.ieeervce.api.siterearnouveau.service;

import org.ieeervce.api.siterearnouveau.entity.Image;
import org.ieeervce.api.siterearnouveau.repository.ImagesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    private ImagesRepository imagesRepository;

    public ImageService(ImagesRepository imagesRepository) {
        this.imagesRepository = imagesRepository;
    }

    public List<Image> list(){
        return imagesRepository.findAll();
    }

    public List<Image> listByCategory(int eventCategory){
        return imagesRepository.findByEventCategory(eventCategory);
    }

    /**
     * Get the bytes of an image by its ID.
     * @param imageId Image ID
     * @return Optional representing bytes of the image if present. 
     */
    public Optional<byte[]> getBytesByImageId(int imageId){
        return imagesRepository.findById(imageId).map(Image::getImageBytes);
    }
}
