package com.inventory.inventorymanagement.dto;


import com.inventory.inventorymanagement.enums.PurchaseStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class PurchaseDTO {

    private Integer purchaseId;


    @NotNull(message = "Supplier is required")
    private Integer supplierId;


    @NotNull(message = "Purchase date is required")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate purchaseDate;


//    @NotNull(message = "Total amount is required")
    @Min(value = 0, message = "Total amount cannot be negative")
    private Double totalAmount;


    @NotNull(message = "Purchase status is required")
    private PurchaseStatus purchaseStatus;


    // List of items
    @NotEmpty(message = "At least one purchase item is required")
    @Valid
    private List<PurchaseItemDTO> purchaseItems = new ArrayList<>();

}
