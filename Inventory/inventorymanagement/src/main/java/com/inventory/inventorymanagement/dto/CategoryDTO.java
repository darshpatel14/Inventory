package com.inventory.inventorymanagement.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {


    private Integer categoryId;


    //@NotBlank(message = "Category name is required")
    //@Size(min = 3, message = "Category name must be at least 3 characters")
    private String categoryName;



   // @NotBlank(message = "Description is required")
    private String description;

}
