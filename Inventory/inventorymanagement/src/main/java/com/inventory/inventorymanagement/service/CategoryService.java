package com.inventory.inventorymanagement.service;

import com.inventory.inventorymanagement.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO save(CategoryDTO categoryDTO);

    List<CategoryDTO> findAll();

    CategoryDTO findById(int theId);

    void deleteById(int theId);
}
