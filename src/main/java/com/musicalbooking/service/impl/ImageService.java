package com.musicalbooking.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicalbooking.dto.ImageDto;
import com.musicalbooking.dto.ProductDto;
import com.musicalbooking.entity.Image;
import com.musicalbooking.entity.Product;
import com.musicalbooking.exceptions.ResourceNotFoundException;
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
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public ImageDto getImageById(Long id) throws ResourceNotFoundException {
        Image image = imageRepository.findById(id).orElse(null);
        ImageDto imageDto = null;

        if ( image != null ) {
            imageDto = objectMapper.convertValue(image, ImageDto.class);
            ProductDto product = productService.getProductById(image.getProduct().getId());
            imageDto.setProductDto(product);
            log.info("The image with id {} has been found: {}", id, imageDto);

            return imageDto;
        } else {
            log.error("The image with id {} was not found", id);
            throw new ResourceNotFoundException("Not found the image with id: " + id);
        }
    }

    @Override
    public List<ImageDto> getImages() {
        List<Image> images = imageRepository.findAll();
        List<ImageDto> imagesDto = null;
//        ImageDto imageDto = null;

        if ( images != null ) {
            imagesDto = images.stream()
                    .map(image -> {
                        ImageDto imageDto = objectMapper.convertValue(image, ImageDto.class);
                        ProductDto product = null;
                        try {
                            product = productService.getProductById(image.getProduct().getId());
                            imageDto.setProductDto(product);
                        } catch (ResourceNotFoundException e) {
                            throw new RuntimeException(e);
                        }

                        return  imageDto;
                    })
                    .collect(Collectors.toList());

            log.info("All these images were found: {}", imagesDto);
        } else {
            log.error("No registered images found");
        }

        return imagesDto;
    }

    @Override
    public ImageDto postImage(Image image) throws ResourceNotFoundException {
        Image imageToPersist = null;
        ImageDto imageDto = null;
        ProductDto product = productService.getProductById(image.getProduct().getId());

        if ( product != null ) {
            imageToPersist = imageRepository.save(image);
            imageDto = objectMapper.convertValue(imageToPersist, ImageDto.class);
            imageDto.setProductDto(product);
            log.info("Image registered successfully: {}", imageDto);
        } else {
            log.error("It was not possible to add the image");
        }

        return imageDto;
    }

    @Override
    public ImageDto updateImage(Image image) {
        return null;
    }

//    @Override
//    public String deleteImageById(Long id) throws ResourceNotFoundException {
//        ImageDto imageDto = getImageById(id);
//        // Image ImageToDelete = objectMapper.convertValue(imageDto, Image.class);
//        ProductDto productDto = productService.getProductById(imageDto.getProductDto().getId());
//        Product product = objectMapper.convertValue(productDto, Product.class);
//
//        if ( product != null ) {
//            Image imageToDelete = product.getImages()
//                    .stream()
//                    .filter(image -> image.getId().equals(id))
//                    .findFirst().orElse(null);
//
//            if ( imageToDelete != null ) {
//                log.info("IMAGE TO DELETE = " + imageToDelete);
//                product.getImages().remove(imageToDelete);
//                imageRepository.delete(imageToDelete);
//                log.warn("The image with id {} has been delete", id);
//
//                return "The image has been removed successfully";
//            }
//        }
//
//        return "The image was not removed";
//
//    }


    @Override
    public String deleteImageById(Long id) throws ResourceNotFoundException {
        ImageDto imageDto = getImageById(id);
        if ( productService.getProductById(imageDto.getProductDto().getId()) != null ) {
                imageRepository.deleteById(id);
                log.warn("The image with id {} has been delete", id);

                return "The image has been removed successfully";
        }

        return "The image was not removed";

    }
}
