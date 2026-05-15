package com.inventory.inventorymanagement.validators;

import com.inventory.inventorymanagement.dao.SupplierRepository;
import com.inventory.inventorymanagement.dto.SupplierDTO;
import com.inventory.inventorymanagement.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SupplierValidator {

    @Autowired
    private SupplierRepository supplierRepository;


    public void validateSupplier(SupplierDTO supplierDTO) {

        if (supplierDTO == null) {
            throw new BadRequestException("Supplier data cannot be null");
        }

        if (supplierRepository.existsBySupplierNameIgnoreCase(supplierDTO.getSupplierName())) {
            throw new BadRequestException("Supplier with this name already exists");
        }
        

        if (supplierRepository.existsByEmailIgnoreCase(supplierDTO.getEmail())) {
            throw new BadRequestException("Supplier with this email already exists");
        }

        if (supplierRepository.existsByGstNo(supplierDTO.getGstNo())) {
            throw new BadRequestException("Supplier with this GST number already exists");
        }
    }

}
