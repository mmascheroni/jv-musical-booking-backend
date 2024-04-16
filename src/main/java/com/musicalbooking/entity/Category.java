package com.musicalbooking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId")
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "The category name cannot be empty or null")
    @Size(min = 3, max = 50, message = "The category name must have a minimum of 3 characters and maximum of 50")
    private String categoryName;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Product> product;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category(String categoryName, List<Product> product) {
        this.categoryName = categoryName;
        this.product = product;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }
}
