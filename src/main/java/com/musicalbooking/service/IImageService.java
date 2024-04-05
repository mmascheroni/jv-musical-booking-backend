package com.musicalbooking.service;

import com.musicalbooking.dto.ImageDto;
import com.musicalbooking.entity.Image;

import java.util.List;

public interface IImageService {

    ImageDto getImageById(Long id);

    List<ImageDto> getImages();

    ImageDto postImage(Image image);

    ImageDto updateImage(Image image);

    String deleteImageById(Long id);
}
