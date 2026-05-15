package com.inventory.inventorymanagement.controller;

import com.inventory.inventorymanagement.dto.SalesDTO;
import com.inventory.inventorymanagement.service.CustomerService;
import com.inventory.inventorymanagement.service.ProductService;
import com.inventory.inventorymanagement.service.SalesService;
import com.inventory.inventorymanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@CrossOrigin(origins = "http://localhost:3000")
public class SalesController {

    @Autowired
    private SalesService salesService;



    @GetMapping("/sales")
    public List<SalesDTO> getAllSales(){
        return salesService.findAll();
    }

    @GetMapping("/sales/{salesId}")
    public SalesDTO getSalesById(@PathVariable int salesId){
        return salesService.findById(salesId);
    }


    @PostMapping("/sales")
    public SalesDTO saveSales(@Valid @RequestBody SalesDTO salesDTO){
        return salesService.saveSales(salesDTO);
    }

    @DeleteMapping("/sales/{salesId}")
    public String deleteSales(@PathVariable int salesId){
        salesService.deleteById(salesId);
        return "Sales for id - "+ salesId + " Deleted Successfully";
    }

}
