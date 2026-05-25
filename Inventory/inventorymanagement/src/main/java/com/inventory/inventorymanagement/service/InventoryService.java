package com.inventory.inventorymanagement.service;

import com.inventory.inventorymanagement.dto.InventoryDTO;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface InventoryService {

    List<InventoryDTO> getAllInventory();

    List<InventoryDTO> getInventoryByProductName(String productName);

    List<String> getAllCategoryNames();

    List<InventoryDTO> getByCategoryAndSupplier(String categoryName, String supplierName);

    List<InventoryDTO> filterInventory(String categoryName, String supplierName, String stockFilter);

    Page<InventoryDTO> findAll(Pageable pageable);
}