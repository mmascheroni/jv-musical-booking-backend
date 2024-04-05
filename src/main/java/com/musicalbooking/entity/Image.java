package com.musicalbooking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "images")
@Getter
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "product", nullable = false)
    @ManyToOne
    @JoinColumn(name = "images")
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
