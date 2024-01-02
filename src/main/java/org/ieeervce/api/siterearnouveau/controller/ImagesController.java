package org.ieeervce.api.siterearnouveau.controller;

import java.util.List;

import org.ieeervce.api.siterearnouveau.entity.Image;
import org.ieeervce.api.siterearnouveau.service.ImageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/image")
public class ImagesController {
    private ImageService imageService;

    public ImagesController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    public List<Image> list() {
        return imageService.list();
    }

    @GetMapping("/{iid}")
    public ResponseEntity<byte[]> get(@PathVariable int iid) throws Exception {
        byte[] image = imageService.getBytesByImageId(iid).orElseThrow(() -> new Exception("Not found"));

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(image);
    }
}
