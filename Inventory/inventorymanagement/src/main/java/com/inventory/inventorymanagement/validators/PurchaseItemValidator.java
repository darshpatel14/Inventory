package com.inventory.inventorymanagement.validators;

import com.inventory.inventorymanagement.dto.PurchaseItemDTO;
import com.inventory.inventorymanagement.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class PurchaseItemValidator {

    public void validatePurchaseItem(PurchaseItemDTO item) {


        //Validate totalCost = quantity * unitCost
        double expectedTotal = item.getQuantity() * item.getUnitCost();

        if (item.getTotalCost() != null && item.getTotalCost() != expectedTotal) {
            throw new BadRequestException("Total cost mismatch for product: " + item.getProductId());
        }

        // Optional: Auto-set totalCost if null
        if (item.getTotalCost() == null) {
            item.setTotalCost(expectedTotal);
        }
    }
}




//        // 1. Validate productId format (example business rule)
//        if (!item.getProductId().startsWith("PROD")) {
//            throw new RuntimeException("Invalid product ID format");
//        }

