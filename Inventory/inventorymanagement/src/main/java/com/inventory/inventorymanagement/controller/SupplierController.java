package com.inventory.inventorymanagement.controller;

import com.inventory.inventorymanagement.dto.SupplierDTO;
import com.inventory.inventorymanagement.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory/alldata/suppliers")
@CrossOrigin(origins = "http://localhost:3000")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public List<SupplierDTO> getAllSuppliers() {
        return supplierService.findAll();
    }

    @GetMapping("/{supplierId}")
    public SupplierDTO getSupplierById(@PathVariable int supplierId) {
        return supplierService.findById(supplierId);
    }

    @PostMapping
    public SupplierDTO saveSupplier(@Valid @RequestBody SupplierDTO supplierDTO) {
        return supplierService.save(supplierDTO);
    }

    @DeleteMapping("/{supplierId}")
    public String deleteSupplier(@PathVariable int supplierId) {
        supplierService.deleteById(supplierId);
        return "Supplier Deleted Successfully";
    }
}
