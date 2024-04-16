package com.musicalbooking.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicalbooking.dto.CategoryDto;
import com.musicalbooking.dto.ProductDto;
import com.musicalbooking.entity.Category;
import com.musicalbooking.entity.Product;
import com.musicalbooking.exceptions.BadRequestException;
import com.musicalbooking.exceptions.ResourceNotFoundException;
import com.musicalbooking.repository.ProductRepository;
import com.musicalbooking.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public ProductDto getProductById(Long id) throws ResourceNotFoundException {

        Product product = productRepository.findById(id).orElse(null);
        ProductDto productDto = null;

        if ( product != null ) {
            productDto = objectMapper.convertValue(product, ProductDto.class);
            log.info("The product with id {} has been found: {}", id, productDto);

            return productDto;
        } else {
            log.error("The product with id {} was not found", id);
            throw new ResourceNotFoundException("Not found the product with id: " + id);
        }

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
    public Page<ProductDto> getProductsByPageable(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<Product> products = productRepository.findAll(pageRequest);
        Page<ProductDto> productsDto = products.map(product -> objectMapper.convertValue(product, ProductDto.class));

        log.info("All these products were found by page {} and size {}: {}", page, size, productsDto);
        return productsDto;
    }

    @Override
    public ProductDto postProduct(Product product) throws BadRequestException, ResourceNotFoundException {
        ProductDto productDto = null;

        if ( productRepository.findByName(product.getName()) != null ) {
            log.error("The product name is already exists in the database");

            throw new BadRequestException("The product name is already exists in the database");
        }

        CategoryDto categoryDto = categoryService.getCategoryById(product.getCategory().getId());

        if ( categoryDto == null ) {
            log.error("The category is not exists in the database");

            throw new ResourceNotFoundException("Not found category with id: " + product.getCategory().getId());
        }

        Product productToPersist = productRepository.save(product);
        productDto = objectMapper.convertValue(productToPersist, ProductDto.class);
        productDto.setCategory(categoryDto);
        log.info("Product registered successfully: {}", productDto);

        return productDto;
    }

    @Override
    public ProductDto updateProduct(Product product) {
        return null;
    }

    @Override
    public String deleteProductById(Long id) throws ResourceNotFoundException {
        if ( getProductById(id) != null ) {
            productRepository.deleteById(id);
            log.warn("The product with id {} has been delete", id);
        }

        return "The product has been removed successfully";
    }
}
