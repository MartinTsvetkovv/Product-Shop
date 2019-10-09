package com.tsvetkov.productshop.productshop.repository;

import com.tsvetkov.productshop.productshop.domain.entities.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {


//    @Query(value = "SELECT p.id, p.name, p.description, p.price, p.image_url FROM products p " +
//            "Join products_categories as pc ON pc.product_id=p.id " +
//            "JOIN categories as c ON pc.category_id = c.id WHERE c.category_name= :category_name", nativeQuery = true)
    //Set<Product> findByCategory(@Param("category_name") String category_name);
}
