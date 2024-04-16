package com.musicalbooking.service;

import com.musicalbooking.dto.ProductDto;
import com.musicalbooking.entity.Product;
import com.musicalbooking.exceptions.BadRequestException;
import com.musicalbooking.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {

    ProductDto getProductById(Long id) throws ResourceNotFoundException;

    List<ProductDto> getProducts();

    Page<ProductDto> getProductsByPageable(int page, int size);

    ProductDto postProduct(Product product) throws BadRequestException, ResourceNotFoundException;

    ProductDto updateProduct(Product product);

    String deleteProductById(Long id) throws ResourceNotFoundException;
}
