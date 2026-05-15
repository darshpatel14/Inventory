package com.inventory.inventorymanagement.service;

import com.inventory.inventorymanagement.dto.InventoryDTO;

import java.util.List;

public interface InventoryService {

    List<InventoryDTO> getAllInventory();
}
