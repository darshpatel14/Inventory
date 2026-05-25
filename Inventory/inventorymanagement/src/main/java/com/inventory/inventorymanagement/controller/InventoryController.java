package com.inventory.inventorymanagement.controller;

import com.inventory.inventorymanagement.dto.EmailRequest;
import com.inventory.inventorymanagement.dto.InventoryDTO;
import com.inventory.inventorymanagement.service.InventoryExportService;
import com.inventory.inventorymanagement.service.InventoryService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/inventory/alldata")
@CrossOrigin(origins = "http://localhost:3000")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private InventoryExportService exportService;

    @GetMapping("/data")
    public Page<InventoryDTO> getAllInventory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "currentStock") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size,sort);

        return inventoryService.findAll(pageable);
    }

    @GetMapping("/filter")
    public List<InventoryDTO> filterInventory(
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String supplierName,
            @RequestParam(required = false) String stockFilter
    ) {

        return inventoryService.filterInventory(
                categoryName,
                supplierName,
                stockFilter
        );
    }

    @GetMapping("/filter/product")
    public List<InventoryDTO> getInventoryByProduct(
            @RequestParam String productName
    ) {

        return inventoryService.getInventoryByProductName(productName);
    }

    @GetMapping("/export/{type}")
    public void exportInventory(
            @PathVariable String type,
            @RequestParam String fromDate,
            @RequestParam String toDate,
            HttpServletResponse response
    ) throws IOException {

        exportService.exportInventory(
                type,
                fromDate,
                toDate,
                response
        );
    }

    @PostMapping("/email-report")
    public String emailReport(
            @RequestBody EmailRequest request
    ) {

        try {

            exportService.emailInventoryReport(
                    request.getEmail(),
                    request.getType(),
                    request.getFromDate(),
                    request.getToDate()
            );

            return "Email Sent Successfully";

        } catch (Exception e) {

            return "Error : " + e.getMessage();
        }
    }
}