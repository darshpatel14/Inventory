package com.inventory.inventorymanagement.validators;

import com.inventory.inventorymanagement.dao.CategoryRepository;
import com.inventory.inventorymanagement.dto.CategoryDTO;
import com.inventory.inventorymanagement.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class CategoryValidator {

    private CategoryRepository categoryRepository;

    public CategoryValidator(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void categoryCreateValidation(CategoryDTO categoryDTO) {

       if (categoryDTO.getCategoryName() == null || categoryDTO.getCategoryName().trim().isEmpty()) {
           throw new BadRequestException("Category name cannot be empty");
       }

       if (categoryDTO.getDescription() == null || categoryDTO.getDescription().trim().isEmpty()) {
           throw new BadRequestException("Description cannot be empty");
       }

       if (categoryDTO.getCategoryName().length() < 3) {
           throw new BadRequestException("Category name must be at least 3 characters");
       }

        // DUPLICATE CHECK
        if (categoryRepository.existsByCategoryName(categoryDTO.getCategoryName())) {
            throw new BadRequestException("Category name already exists");
        }

   }



//        if (categoryDTO == null) {
//            throw new BadRequestException("Category object cannot be null");
//        }

}



// global exception handler
// custom exception
// React SPA REST Controller
// api response dto(standard) java generate api
