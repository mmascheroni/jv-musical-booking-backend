package com.musicalbooking.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {

    private Long id;
    private String url;
    private ProductDto productDto;

    public ImageDto(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    @Override
    public String toString() {
        return "Image {" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", productDto=" + productDto +
                '}';
    }
}
