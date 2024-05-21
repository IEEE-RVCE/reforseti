package org.ieeervce.api.siterearnouveau.controller;

import org.ieeervce.api.siterearnouveau.dto.ResultsDTO;
import org.ieeervce.api.siterearnouveau.dto.image.ImageDescriptionDTO;
import org.ieeervce.api.siterearnouveau.service.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping("/api/image")
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
    public ResponseEntity<byte[]> get(@PathVariable int iid) throws Exception {
        byte[] image = imageService.getBytesByImageId(iid).orElseThrow(() -> new Exception("Not found"));

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(image);
    }
}
