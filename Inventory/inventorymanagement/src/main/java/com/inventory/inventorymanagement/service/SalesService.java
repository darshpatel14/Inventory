package com.inventory.inventorymanagement.service;

import com.inventory.inventorymanagement.dto.SalesDTO;
import com.inventory.inventorymanagement.entity.Sales;

import java.util.List;

public interface SalesService {

    SalesDTO saveSales(SalesDTO salesDTO);

    List<SalesDTO> findAll();

    SalesDTO findById(int theId);

    void deleteById(int theId);
}
