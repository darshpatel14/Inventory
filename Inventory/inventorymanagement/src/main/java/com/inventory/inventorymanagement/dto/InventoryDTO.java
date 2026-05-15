package com.inventory.inventorymanagement.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDTO {

    private Integer id;

    private String productName;

    private Integer stockIn = 0;

    private Integer stockOut = 0;

    private Integer currentStock = 0;

    private LocalDateTime lastUpdated;


}
