package com.inventory.inventorymanagement.validators;

import com.inventory.inventorymanagement.dao.CustomerRepository;
import com.inventory.inventorymanagement.dto.*;
import com.inventory.inventorymanagement.exception.BadRequestException;
import com.inventory.inventorymanagement.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class SalesValidator {


    private final CustomerRepository customerRepository;

    public SalesValidator(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void validateSales(SalesDTO salesDTO){

        if (salesDTO.getSalesItems() == null) {
            throw new BadRequestException("Atleast one sales item is required");
        }

        if (salesDTO.getSalesDate().isAfter(java.time.LocalDate.now())) {
            throw new BadRequestException("Sales date cannot be in the future");
        }

        if (salesDTO.getModeOfPayment() == null) {
            throw new BadRequestException("Mode of payment is required");
        }



        double calculatedTotal = 0;

        for (SalesItemDTO item : salesDTO.getSalesItems()) {

            double itemTotal = item.getQuantity() * item.getUnitPrice();
            calculatedTotal += itemTotal;
        }


    }
}
