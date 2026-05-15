package com.inventory.inventorymanagement.controller;

import com.inventory.inventorymanagement.dto.PurchaseDTO;
import com.inventory.inventorymanagement.service.PurchaseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@CrossOrigin(origins = "http://localhost:3000")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;


    @GetMapping("/purchase")
    public List<PurchaseDTO> getAllPurchase(){
        return purchaseService.findAll();
    }

    @GetMapping("/purchase/{purchaseId}")
    public PurchaseDTO getPurchaseById(@PathVariable int purchaseId){
        return purchaseService.findById(purchaseId);
    }


    @PostMapping("/purchase")
    public PurchaseDTO savePurchase(@Valid @RequestBody PurchaseDTO purchaseDTO){
        return purchaseService.savePurchase(purchaseDTO);
    }

    @DeleteMapping("/purchase/{purchaseId}")
    public String deletePurchase(@PathVariable int purchaseId){
        purchaseService.deleteById(purchaseId);
        return "Purchase for id - "+ purchaseId + " Deleted Successfully";
    }
}