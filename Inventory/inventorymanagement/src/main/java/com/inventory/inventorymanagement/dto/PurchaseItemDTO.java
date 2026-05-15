package com.inventory.inventorymanagement.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseItemDTO {


    private Integer id;


    // Instead of Product entity → use productId
    @NotNull(message = "Product is required")
    private Integer productId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Unit cost is required")
    @Min(value = 1, message = "Unit cost must be greater than 0")
    private Double unitCost;


    private Double totalCost;



}
