package com.musicalbooking.service;

import com.musicalbooking.dto.ImageDto;
import com.musicalbooking.entity.Image;
import com.musicalbooking.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IImageService {

    ImageDto getImageById(Long id) throws ResourceNotFoundException;

    List<ImageDto> getImages();

    ImageDto postImage(Image image) throws ResourceNotFoundException;

    ImageDto updateImage(Image image);

    String deleteImageById(Long id) throws ResourceNotFoundException;
}
