package com.inventory.inventorymanagement.controller;

import com.inventory.inventorymanagement.dto.EmailRequest;
import com.inventory.inventorymanagement.dto.InventoryDTO;
import com.inventory.inventorymanagement.service.InventoryExportService;
import com.inventory.inventorymanagement.service.InventoryService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/inventory")
@CrossOrigin(origins = "http://localhost:3000")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private InventoryExportService exportService;


    @GetMapping("/data")
    public List<InventoryDTO> getAllInventory(){
        return inventoryService.getAllInventory();
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



    // EMAIL REPORT API
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

        } catch (Exception e){

            return "Error : " + e.getMessage();
        }
    }
}