package com.musicalbooking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productoId")
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    @NotBlank(message = "The product name cannot be empty or null")
    @Size(min = 3, max = 50, message = "The product name must have a minimum of 3 characters and maximum of 50")
    private String name;

    @Column(name = "description", nullable = false)
    @NotBlank(message = "The product description cannot be empty or null")
    @Size(min = 3, max = 200, message = "The product description must have a minimum of 3 characters and maximum of 200")
    private String description;

    @OneToMany(mappedBy = "product", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Image> images;

    public Product(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Product(String name, String description, List<Image> images) {
        this.name = name;
        this.description = description;
        this.images = images;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
