package com.musicalbooking.service;

import com.musicalbooking.dto.ProductDto;
import com.musicalbooking.entity.Product;

public interface IProductService {

    ProductDto getProductById(Long id);

    ProductDto getProducts();

    ProductDto postProduct(Product product);

    ProductDto updateProduct(Product product);

    String deleteProductById(Long id);
}
