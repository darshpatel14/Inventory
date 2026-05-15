package com.inventory.inventorymanagement.dao;

import com.inventory.inventorymanagement.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT COUNT(c) FROM Category c")
    Long getTotalCategory();

    boolean existsByCategoryName(String categoryName);

}
