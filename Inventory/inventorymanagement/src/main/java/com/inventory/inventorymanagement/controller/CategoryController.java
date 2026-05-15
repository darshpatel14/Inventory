package com.inventory.inventorymanagement.controller;


import com.inventory.inventorymanagement.dto.CategoryDTO;
import com.inventory.inventorymanagement.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/inventory")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {

    private final CategoryService categoryService;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/categories")
    public List<CategoryDTO> getAllCategory(){
       return categoryService.findAll();
    }

    @GetMapping("/categories/{categoryId}")
    public CategoryDTO getCategoryById(@PathVariable int categoryId){
        return categoryService.findById(categoryId);
    }


    @PostMapping("/categories")
    public CategoryDTO saveCategory(@Valid @RequestBody CategoryDTO categoryDTO){
       return categoryService.save(categoryDTO);
    }

    @DeleteMapping("/categories/{categoryId}")
    public String deleteCategory(@PathVariable int categoryId){
        categoryService.deleteById(categoryId);
        return "Category Deleted Successfully";
    }



}

