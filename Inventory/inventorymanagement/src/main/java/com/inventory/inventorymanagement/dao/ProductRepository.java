package com.inventory.inventorymanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.inventory.inventorymanagement.entity.Product;
import org.springframework.data.jpa.repository.Query;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT COUNT(p) FROM Product p")
    Long getTotalProducts();

    boolean existsByProductNameIgnoreCase(String productName);
}
