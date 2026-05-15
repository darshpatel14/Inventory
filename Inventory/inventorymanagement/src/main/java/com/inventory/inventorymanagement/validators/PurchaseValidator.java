package com.inventory.inventorymanagement.validators;

import com.inventory.inventorymanagement.dto.PurchaseDTO;
import com.inventory.inventorymanagement.dto.PurchaseItemDTO;
import com.inventory.inventorymanagement.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class PurchaseValidator {

    public void validatePurchase(PurchaseDTO purchaseDTO) {

        // 1. Validate purchase status values (business rule)
        if (purchaseDTO.getPurchaseStatus() == null) {
            throw new BadRequestException("Purchase status is required");
        }


        // 2. Validate total amount = sum of all item totalCost
        double calculatedTotal = 0;

        for (PurchaseItemDTO item : purchaseDTO.getPurchaseItems()) {

            double itemTotal = item.getQuantity() * item.getUnitCost();
            calculatedTotal += itemTotal;
        }

//        if (purchaseDTO.getTotalAmount() != calculatedTotal) {
//            throw new BadRequestException("Total amount mismatch with purchase items");
//        }

        // 3. Purchase date should not be in future
        if (purchaseDTO.getPurchaseDate().isAfter(java.time.LocalDate.now())) {
            throw new BadRequestException("Purchase date cannot be in the future");
        }
    }


}
