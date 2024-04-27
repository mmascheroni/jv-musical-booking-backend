package com.musicalbooking.repository;

import com.musicalbooking.entity.Category;
import com.musicalbooking.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
    List<Product> findByCategoryId(Long categoryId);

}
