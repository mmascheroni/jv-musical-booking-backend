package com.musicalbooking.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "images")
@Getter
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imageId")
    private Long id;

    @Column(name = "url", nullable = false)
    @NotBlank(message = "The image url cannot be empty or null")
    private String url;

    @ManyToOne
    @JoinColumn(name = "productoId", nullable = false)
    @JsonIgnoreProperties("images")
    private Product product;

    public Image(String url, Product product) {
        this.url = url;
        this.product = product;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setProductId(Product product) {
        this.product = product;
    }
}
