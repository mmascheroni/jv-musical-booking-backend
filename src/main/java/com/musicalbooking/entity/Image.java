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
    @Column(name = "imageId")
    private Long id;

    @Column(name = "url", nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "productoId")
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
