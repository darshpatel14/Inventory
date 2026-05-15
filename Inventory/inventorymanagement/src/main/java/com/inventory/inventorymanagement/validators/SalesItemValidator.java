package com.inventory.inventorymanagement.validators;


import com.inventory.inventorymanagement.dto.SalesItemDTO;
import com.inventory.inventorymanagement.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class SalesItemValidator {

    public void validateSalesItem(SalesItemDTO salesItemDTO){

        double expectedTotal = salesItemDTO.getQuantity() * salesItemDTO.getUnitPrice();

        if (salesItemDTO.getTotalPrice() != null && salesItemDTO.getTotalPrice() != expectedTotal) {
            throw new BadRequestException("Total cost mismatch for product: " + salesItemDTO.getProductId());
        }

        // Optional: Auto-set totalCost if null
        if (salesItemDTO.getTotalPrice() == null) {
            salesItemDTO.setTotalPrice(expectedTotal);
        }
    }
}
