package com.inventory.inventorymanagement.service;

import com.inventory.inventorymanagement.dto.PurchaseDTO;
import com.inventory.inventorymanagement.entity.Purchase;

import java.util.List;

public interface PurchaseService {

    PurchaseDTO savePurchase(PurchaseDTO purchaseDTO);

    PurchaseDTO findById(int theId);

    List<PurchaseDTO> findAll();

    void deleteById(int theId);
}
