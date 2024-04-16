package com.musicalbooking.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicalbooking.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private List<ImageDto> images;

    private CategoryDto category;

    @Autowired
    private static ObjectMapper objectMapper;

    public static ProductDto fromProducto(Product product) {
        List<ImageDto> imagesDto = product.getImages()
                .stream()
                .map(image -> objectMapper.convertValue(image, ImageDto.class))
                .collect(Collectors.toList());

        CategoryDto categoryDto = objectMapper.convertValue(product.getCategory(), CategoryDto.class);

        return new ProductDto(product.getId(), product.getName(), product.getDescription(), imagesDto, categoryDto);
    }

    @Override
    public String toString() {
        return "Product {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", images=" + images +
                ", category=" + category +
                '}';
    }
}
