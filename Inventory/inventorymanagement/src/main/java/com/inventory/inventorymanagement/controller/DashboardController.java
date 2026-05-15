package com.inventory.inventorymanagement.controller;

import com.inventory.inventorymanagement.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/inventory")
@CrossOrigin(origins = "http://localhost:3000")
public class DashboardController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private SalesRepository salesRepository;

    @GetMapping("/dashboard")
    public Map<String, Object> dashboard() {

        Map<String, Object> response = new HashMap<>();

        response.put("totalProducts", productRepository.getTotalProducts());
        response.put("totalCategories", categoryRepository.getTotalCategory());
        response.put("totalSuppliers", supplierRepository.getTotalSuppliers());
        response.put("totalPurchase", purchaseRepository.getTotalPurchase());
        response.put("totalSales", salesRepository.getTotalSales());

        return response;
    }
}
