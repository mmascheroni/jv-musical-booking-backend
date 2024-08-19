package com.musicalbooking.controller;

import com.musicalbooking.dto.ImageDto;
import com.musicalbooking.exceptions.ResourceNotFoundException;
import com.musicalbooking.service.impl.ImageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/images")
@CrossOrigin
public class ImageController {

    @Autowired
    private ImageService imageService;


    @GetMapping()
    public ResponseEntity<List<ImageDto>> getImages() {
        return ResponseEntity.ok(imageService.getImages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageDto> getImageById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(imageService.getImageById(id));
    }

//    @PostMapping()
//    public ResponseEntity<ImageDto> postImage(@Valid @RequestBody Image image) throws ResourceNotFoundException {
//        return ResponseEntity.status(HttpStatus.CREATED).body(imageService.postImage(image));
//    }

    @PostMapping()
    public ResponseEntity<ImageDto> postImage(@Valid @RequestParam(value = "file", required = false) MultipartFile file, @RequestParam("upload_preset") String uploadPreset, @RequestParam("product_id") Long id) throws ResourceNotFoundException, IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(imageService.uploadAndPostImage(file, uploadPreset, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteImageById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(imageService.deleteImageById(id));
    }

}
