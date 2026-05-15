package com.inventory.inventorymanagement.dto;


import com.inventory.inventorymanagement.entity.Category;
import com.inventory.inventorymanagement.entity.Supplier;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {


    private Integer productId;


    @NotBlank(message = "Product name is required")
    private String productName;


    @NotBlank(message = "Product description is required")
    private String productDescription;


    @NotNull(message = "Price is required")
    @Min(value = 1, message = "Price must be greater than 0")
    private Double price;



    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;



    // Instead of full Category object
    // it does not depend on entity(category) , minimal data transfer , avoid infinite loops
    @NotNull(message = "Category is required")
    private Integer categoryId;


    // Instead of Supplier list
    private List<Integer> supplierIds;


}
