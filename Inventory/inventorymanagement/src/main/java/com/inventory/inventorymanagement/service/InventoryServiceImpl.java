package com.inventory.inventorymanagement.service;

import com.inventory.inventorymanagement.dao.InventoryCriteriaRepository;
import com.inventory.inventorymanagement.dao.InventoryRepository;
import com.inventory.inventorymanagement.dto.InventoryDTO;
import com.inventory.inventorymanagement.entity.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryCriteriaRepository criteriaRepository;



    @Override
    public List<InventoryDTO> getAllInventory() {
        return inventoryRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }



    @Override
    public List<InventoryDTO> getInventoryByProductName(String productName) {
        return inventoryRepository.findByProduct_ProductName(productName)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }



    @Override
    public List<String> getAllCategoryNames() {
        return inventoryRepository.findAll()
                .stream()
                .map(inv -> inv.getProduct().getCategory().getCategoryName())
                .distinct()
                .collect(Collectors.toList());
    }



    @Override
    public List<InventoryDTO> getByCategoryAndSupplier(String categoryName, String supplierName) {
        return inventoryRepository.filterByCategoryAndSupplier(categoryName, supplierName)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }



    @Override
    public List<InventoryDTO> filterInventory(String categoryName,
                                              String supplierName,
                                              String stockFilter) {

        return criteriaRepository.filterInventory(categoryName, supplierName, stockFilter)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }




    @Override
    public Page<InventoryDTO> findAll(Pageable pageable) {

        Page<Inventory> inventoryPage = inventoryRepository.findAll(pageable);

        return inventoryPage.map(this::convertToDTO);
    }



    // DTO mapper
    private InventoryDTO convertToDTO(Inventory inventory) {

        InventoryDTO dto = new InventoryDTO();

        dto.setId(inventory.getId());
        dto.setProductName(inventory.getProduct().getProductName());
        dto.setStockIn(inventory.getStockIn());
        dto.setStockOut(inventory.getStockOut());
        dto.setCurrentStock(inventory.getCurrentStock());
        dto.setLastUpdated(inventory.getLastUpdated());

        return dto;
    }

}
