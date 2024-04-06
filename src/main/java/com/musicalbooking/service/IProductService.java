package com.musicalbooking.service;

import com.musicalbooking.dto.ProductDto;
import com.musicalbooking.entity.Product;

import java.util.List;

public interface IProductService {

    ProductDto getProductById(Long id);

    List<ProductDto> getProducts();

    ProductDto postProduct(Product product);

    ProductDto updateProduct(Product product);

    String deleteProductById(Long id);
}
