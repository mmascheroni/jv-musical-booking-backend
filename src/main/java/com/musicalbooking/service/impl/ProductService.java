package com.musicalbooking.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicalbooking.dto.ProductDto;
import com.musicalbooking.entity.Product;
import com.musicalbooking.repository.ProductRepository;
import com.musicalbooking.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public ProductDto getProductById(Long id) {

        Product product = productRepository.findById(id).orElse(null);
        ProductDto productDto = null;

        if ( product != null ) {
            productDto = objectMapper.convertValue(product, ProductDto.class);
            log.info("The product with id {} has been found: {}", id, product);
        } else {
            log.error("The product with id {} was not found", id);
        }

        return productDto;
    }

    @Override
    public List<ProductDto> getProducts() {

        List<Product> products = productRepository.findAll();
        List<ProductDto> productsDto = null;

        if ( products != null ) {
            productsDto = products.stream()
                    .map(product -> objectMapper.convertValue(product, ProductDto.class))
                    .collect(Collectors.toList());

            log.info("All these products were found: {}", productsDto);
        } else {
            log.error("No registered products found");
        }

        return productsDto;
    }

    @Override
    public ProductDto postProduct(Product product) {
        Product productToPersist = productRepository.save(product);

        ProductDto productDto = objectMapper.convertValue(productToPersist, ProductDto.class);

        log.info("Product registered successfully: {}", productDto);

        return productDto;
    }

    @Override
    public ProductDto updateProduct(Product product) {
        return null;
    }

    @Override
    public String deleteProductById(Long id) {
        if ( getProductById(id) != null ) {
            productRepository.deleteById(id);
            log.warn("The product with id {} has been delete", id);
            return "The product has been removed successfully";
        } else {
            log.error("No registered products with {} found", id);
            return "It has not been possible to delete the product since it does not exist";
        }

    }
}
