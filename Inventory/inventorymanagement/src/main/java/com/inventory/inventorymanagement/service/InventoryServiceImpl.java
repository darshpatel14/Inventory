package com.inventory.inventorymanagement.service;

import com.inventory.inventorymanagement.dao.InventoryRepository;
import com.inventory.inventorymanagement.dto.InventoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService{

    @Autowired
    private InventoryRepository inventoryRepository;


    @Override
    public List<InventoryDTO> getAllInventory() {
        return inventoryRepository.findAll()
                .stream()
                .map(inventory -> {
                    InventoryDTO inventoryDTO = new InventoryDTO();
                    inventoryDTO.setId(inventory.getId());

                    inventoryDTO.setProductName(inventory.getProduct().getProductName());

                    inventoryDTO.setStockIn(inventory.getStockIn());

                    inventoryDTO.setStockOut(inventory.getStockOut());

                    inventoryDTO.setCurrentStock(inventory.getCurrentStock());

                    inventoryDTO.setLastUpdated(inventory.getLastUpdated());

                    return inventoryDTO;
                })
                .collect(Collectors.toList());
    }
}
