package com.tsvetkov.productshop.productshop.repository;

import com.tsvetkov.productshop.productshop.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
