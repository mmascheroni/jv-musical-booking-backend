package com.musicalbooking.repository;

import com.musicalbooking.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Product, Long> {
}
