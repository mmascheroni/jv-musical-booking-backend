package com.musicalbooking.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicalbooking.dto.ImageDto;
import com.musicalbooking.entity.Image;
import com.musicalbooking.repository.ImageRepository;
import com.musicalbooking.service.IImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ImageService implements IImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public ImageDto getImageById(Long id) {
        Image image = imageRepository.findById(id).orElse(null);
        ImageDto imageDto = null;

        if ( image != null ) {
            imageDto = objectMapper.convertValue(image, ImageDto.class);
            log.info("The image with id {} has been found: {}", id, imageDto);
        } else {
            log.error("The image with id {} was not found", id);
        }

        return imageDto;
    }

    @Override
    public List<ImageDto> getImages() {
        List<Image> images = imageRepository.findAll();
        List<ImageDto> imagesDto = null;

        if ( images != null ) {
            imagesDto = images.stream()
                    .map(image -> objectMapper.convertValue(image, ImageDto.class))
                    .collect(Collectors.toList());

            log.info("All these images were found: {}", imagesDto);
        } else {
            log.error("No registered images found");
        }

        return imagesDto;
    }

    @Override
    public ImageDto postImage(Image image) {
        Image imageToPersist = imageRepository.save(image);
        ImageDto imageDto = objectMapper.convertValue(imageToPersist, ImageDto.class);

        log.info("Image registered successfully: {}", imageDto);

        return imageDto;
    }

    @Override
    public ImageDto updateImage(Image image) {
        return null;
    }

    @Override
    public String deleteImageById(Long id) {

        if ( getImageById(id) != null ) {
            imageRepository.deleteById(id);
            log.warn("The image with id {} has been delete", id);
            return "The image has been removed successfully";
        } else {
            log.error("No registered products with {} found", id);
            return "It has not been possible to delete the image since it does not exist";
        }

    }
}
