package com.musicalbooking.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicalbooking.dto.ImageDto;
import com.musicalbooking.dto.ProductDto;
import com.musicalbooking.entity.Image;
import com.musicalbooking.entity.Product;
import com.musicalbooking.exceptions.ResourceNotFoundException;
import com.musicalbooking.repository.ImageRepository;
import com.musicalbooking.service.IImageService;
import com.musicalbooking.utils.upload.UploadImage;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    Dotenv dotenv = Dotenv.configure()
            .directory(".")
            .load();

    String url = dotenv.get("URL");
    // String presetUpload = dotenv.get("PRESET_UPLOAD");

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
            log.warn("No registered images found");
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
            product = productService.getProductById(image.getProduct().getId());
            imageDto.setProductDto(product);

            log.info("Image registered successfully: {}", imageDto);
        }

        return imageDto;
    }

    public ImageDto uploadAndPostImage(MultipartFile imageFile, String uploadPreset, Long productId) throws IOException, ResourceNotFoundException {
        ProductDto productDto = productService.getProductById(productId);
        ImageDto imageDto = null;
        Image imageToPersist = null;

        if (productDto != null) {
            UploadImage uploadImage = new UploadImage();
            try {
                String imageURL = uploadImage.uploadFile(url, imageFile, uploadPreset);

                if (imageURL != null) {
                    Product product = objectMapper.convertValue(productDto, Product.class);
                    Image image = new Image(imageURL, product);
                    imageToPersist = imageRepository.save(image);
                    imageDto = objectMapper.convertValue(imageToPersist, ImageDto.class);
                    productDto = productService.getProductById(image.getProduct().getId());
                    imageDto.setProductDto(productDto);

                    log.info("Image registered successfully: {}", imageDto);
                } else {
                    log.error("The image was not registered");
                }
            } catch (RuntimeException e) {
                log.error("Error uploading the image: {}", e.getMessage());
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Error: The file is unsupported.");
            }
        }

        return imageDto;
    }

    @Override
    public ImageDto updateImage(Image image) {
        return null;
    }


    @Override
    public String deleteImageById(Long id) throws ResourceNotFoundException {
        if ( getImageById(id) != null ) {
                imageRepository.deleteById(id);
                log.warn("The image with id {} has been delete", id);
        }

        return "The image has been removed successfully";

    }
}
