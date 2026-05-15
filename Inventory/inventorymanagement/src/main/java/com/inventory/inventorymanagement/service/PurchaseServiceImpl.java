package com.inventory.inventorymanagement.service;

import com.inventory.inventorymanagement.dao.InventoryRepository;
import com.inventory.inventorymanagement.dao.PurchaseRepository;
import com.inventory.inventorymanagement.dao.SupplierRepository;
import com.inventory.inventorymanagement.dto.PurchaseDTO;
import com.inventory.inventorymanagement.dto.PurchaseItemDTO;
import com.inventory.inventorymanagement.entity.*;
import com.inventory.inventorymanagement.exception.ResourceNotFoundException;
import com.inventory.inventorymanagement.validators.PurchaseItemValidator;
import com.inventory.inventorymanagement.validators.PurchaseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImpl implements PurchaseService {


    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private PurchaseValidator purchaseValidator;

    @Autowired
    private PurchaseItemValidator purchaseItemValidator;

    @Autowired
    private InventoryRepository inventoryRepository;


    @Override
    public PurchaseDTO savePurchase(PurchaseDTO purchaseDTO) {


        // Validate each item first
        for (PurchaseItemDTO item : purchaseDTO.getPurchaseItems()) {
            purchaseItemValidator.validatePurchaseItem(item);
        }

        // Validate purchase
        purchaseValidator.validatePurchase(purchaseDTO);



        // Convert DTO → Entity
        Purchase purchase = new Purchase();

        Supplier supplier = supplierRepository
                .findById(purchaseDTO.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        purchase.setSupplier(supplier);
        purchase.setPurchaseDate(purchaseDTO.getPurchaseDate());
        purchase.setPurchaseStatus(purchaseDTO.getPurchaseStatus());

        double totalAmount = 0;

        for (PurchaseItemDTO itemDTO : purchaseDTO.getPurchaseItems()) {

            PurchaseItem item = new PurchaseItem();

            item.setProductId(itemDTO.getProductId());

            item.setQuantity(itemDTO.getQuantity());

            item.setUnitCost(itemDTO.getUnitCost());

            double total = item.getQuantity() * item.getUnitCost();

            item.setTotalCost(total);

            purchase.addItem(item);

            totalAmount += total;


            // =========================================
            // UPDATE INVENTORY
            // =========================================

            Inventory inventory = inventoryRepository
                    .findByProduct_ProductId(itemDTO.getProductId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Inventory not found for product id : "
                                            + itemDTO.getProductId()));



            // UPDATE STOCK IN
            inventory.setStockIn(
                    inventory.getStockIn() + itemDTO.getQuantity()
            );



            // UPDATE CURRENT STOCK
            inventory.setCurrentStock(
                    inventory.getStockIn() - inventory.getStockOut()
            );



            // UPDATE LAST UPDATED TIME
            inventory.setLastUpdated(java.time.LocalDateTime.now());



            // SAVE INVENTORY
            inventoryRepository.save(inventory);

        }

        purchase.setTotalAmount(totalAmount);

        // Save
        Purchase savedPurchase = purchaseRepository.save(purchase);

        // Convert back Entity → DTO
        purchaseDTO.setPurchaseId(savedPurchase.getPurchaseId());
        purchaseDTO.setTotalAmount(savedPurchase.getTotalAmount());

        return purchaseDTO;
    }

    private PurchaseDTO mapToDTO(Purchase purchase) {

        PurchaseDTO dto = new PurchaseDTO();

        dto.setPurchaseId(purchase.getPurchaseId());
        dto.setSupplierId(purchase.getSupplier().getSupplierId());
        dto.setPurchaseDate(purchase.getPurchaseDate());
        dto.setPurchaseStatus(purchase.getPurchaseStatus());
        dto.setTotalAmount(purchase.getTotalAmount());

        List<PurchaseItemDTO> items = purchase.getPurchaseItems()
                .stream()
                .map(item -> {
                    PurchaseItemDTO itemDTO = new PurchaseItemDTO();
                    itemDTO.setId(item.getId());
                    itemDTO.setProductId(Integer.valueOf(item.getProductId()));
                    itemDTO.setQuantity(item.getQuantity());
                    itemDTO.setUnitCost(item.getUnitCost());
                    itemDTO.setTotalCost(item.getTotalCost());
                    return itemDTO;
                })
                .collect(Collectors.toList());

        dto.setPurchaseItems(items);

        return dto;
    }

    @Override
    public PurchaseDTO findById(int theId) {
        Purchase purchase = purchaseRepository.findById(theId)
                .orElseThrow(()-> new ResourceNotFoundException("Purchase id not found - " + theId));

        return mapToDTO(purchase);

    }

    @Override
    public List<PurchaseDTO> findAll() {
        return purchaseRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(int theId) {

        if (!purchaseRepository.existsById(theId)) {
            throw new ResourceNotFoundException("Purchases not found with id: " + theId);
        }

        purchaseRepository.deleteById(theId);

    }
}


