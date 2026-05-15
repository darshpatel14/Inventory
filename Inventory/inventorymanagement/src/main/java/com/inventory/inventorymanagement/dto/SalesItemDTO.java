package com.inventory.inventorymanagement.dto;


import com.inventory.inventorymanagement.entity.Sales;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesItemDTO {


    private Integer id;


    @NotNull(message = "Product is required")
    private Integer productId;


    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;


    @NotNull(message = "Unit price is required")
    @Min(value = 1, message = "Unit price must be greater than 0")
    private Double unitPrice;


    private Double totalPrice;

}
