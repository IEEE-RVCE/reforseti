package org.ieeervce.api.siterearnouveau.controller;

import io.micrometer.core.annotation.Timed;
import org.ieeervce.api.siterearnouveau.dto.ResultsDTO;
import org.ieeervce.api.siterearnouveau.dto.image.ImageDescriptionDTO;
import org.ieeervce.api.siterearnouveau.entity.Image;
import org.ieeervce.api.siterearnouveau.exception.DataNotFoundException;
import org.ieeervce.api.siterearnouveau.service.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/image")
@Timed
public class ImagesController {
    private ImageService imageService;
    private ModelMapper modelMapper;

    public ImagesController(ImageService imageService, ModelMapper modelMapper) {
        this.imageService = imageService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResultsDTO<Stream<ImageDescriptionDTO>> list() {
        Stream<ImageDescriptionDTO> imageDescriptionStream = imageService.list().stream().map(e -> modelMapper.map(e, ImageDescriptionDTO.class));
        return new ResultsDTO<>(imageDescriptionStream);
    }

    @GetMapping("/category/{eventCategory}")
    public ResultsDTO<Stream<ImageDescriptionDTO>> listByCategory(@PathVariable int eventCategory){
        Stream<ImageDescriptionDTO> imageDescriptionStream = imageService.listByCategory(eventCategory).stream().map(e -> modelMapper.map(e, ImageDescriptionDTO.class));
        return new ResultsDTO<>(imageDescriptionStream);
    }


    @GetMapping("/{iid}")
    public ResponseEntity<byte[]> get(@PathVariable int iid) throws DataNotFoundException {
        byte[] image = imageService.getBytesByImageId(iid).orElseThrow(DataNotFoundException::new);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(image);
    }

    @DeleteMapping("{iid}")
    public ResultsDTO<Void> delete(@PathVariable int iid){
        imageService.delete(iid);
        return new ResultsDTO<>(null);
    }

    @PostMapping
    public ResultsDTO<ImageDescriptionDTO> create(@RequestParam int eventCategory, @RequestParam String altText, @RequestPart("image") MultipartFile imageFile) throws IOException {
        Image image = getImageFromRequestParts(eventCategory, altText, imageFile);
        Image createdImage = imageService.createOrUpdate(image);
        ImageDescriptionDTO createdImageDescription = modelMapper.map( createdImage, ImageDescriptionDTO.class);
        return new ResultsDTO<>(createdImageDescription);
    }

    @PutMapping("/{iid}")
    public ResultsDTO<ImageDescriptionDTO> create(@RequestParam int eventCategory, @RequestParam String altText, @RequestPart("image") MultipartFile imageFile,@PathVariable int iid) throws IOException {
        Image image = getImageFromRequestParts(eventCategory, altText, imageFile);
        image.setImageId(iid);
        Image createdImage = imageService.createOrUpdate(image);
        ImageDescriptionDTO createdImageDescription = modelMapper.map( createdImage, ImageDescriptionDTO.class);
        return new ResultsDTO<>(createdImageDescription);
    }

    private static Image getImageFromRequestParts(int eventCategory, String altText, MultipartFile imageFile) throws IOException {
        Image image = new Image();
        image.setEventCategory(eventCategory);
        image.setImageBytes(imageFile.getBytes());
        image.setAltText(altText);
        return image;
    }
}
