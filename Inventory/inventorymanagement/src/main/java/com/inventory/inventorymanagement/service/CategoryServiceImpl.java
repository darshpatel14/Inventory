package com.inventory.inventorymanagement.service;

import com.inventory.inventorymanagement.dao.CategoryRepository;
import com.inventory.inventorymanagement.dto.CategoryDTO;
import com.inventory.inventorymanagement.entity.Category;
import com.inventory.inventorymanagement.exception.BadRequestException;
import com.inventory.inventorymanagement.exception.ResourceNotFoundException;
import com.inventory.inventorymanagement.validators.CategoryValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{



    private CategoryRepository categoryRepository;
    private CategoryValidator categoryValidator;


    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryValidator categoryValidator) {
        this.categoryRepository = categoryRepository;
        this.categoryValidator = categoryValidator;
    }

    // DTO -> ENTITY
    private Category convertDtoToEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCategoryId(categoryDTO.getCategoryId());
        category.setCategoryName(categoryDTO.getCategoryName());
        category.setDescription(categoryDTO.getDescription());
        return category;
    }

    // ENTITY -> DTO
    private CategoryDTO convertEntityToDto(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(category.getCategoryId());
        categoryDTO.setCategoryName(category.getCategoryName());
        categoryDTO.setDescription(category.getDescription());
        return categoryDTO;
    }



    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {

        categoryValidator.categoryCreateValidation(categoryDTO);

        Category category = convertDtoToEntity(categoryDTO);

        Category savedCategory = categoryRepository.save(category);

        return convertEntityToDto(savedCategory);
    }



    @Override
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }



    @Override
    public CategoryDTO findById(int theId) {
        Category category = categoryRepository.findById(theId)
                .orElseThrow(()-> new ResourceNotFoundException("Category id not found - " + theId));

        return convertEntityToDto(category);
    }



    @Override
    public void deleteById(int theId) {

        if (!categoryRepository.existsById(theId)) {
            throw new ResourceNotFoundException("Category not found with id: " + theId);
        }

        categoryRepository.deleteById(theId);
    }
}
