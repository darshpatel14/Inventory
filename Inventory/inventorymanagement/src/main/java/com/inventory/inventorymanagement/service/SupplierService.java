package com.inventory.inventorymanagement.service;

import com.inventory.inventorymanagement.dto.CategoryDTO;
import com.inventory.inventorymanagement.dto.SupplierDTO;
import com.inventory.inventorymanagement.entity.Supplier;

import java.util.List;

public interface SupplierService {

    SupplierDTO save(SupplierDTO supplierDTO);

    List<SupplierDTO> findAll();

    SupplierDTO findById(int theId);

    void deleteById(int theId);
}
