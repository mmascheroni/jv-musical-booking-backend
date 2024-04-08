package com.musicalbooking.controller;

import com.musicalbooking.dto.ProductDto;
import com.musicalbooking.entity.Product;
import com.musicalbooking.exceptions.ResourceNotFoundException;
import com.musicalbooking.service.impl.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping()
    public ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping()
    public ResponseEntity<ProductDto> postProduct(@Valid @RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.postProduct(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(productService.deleteProductById(id));
    }
}
