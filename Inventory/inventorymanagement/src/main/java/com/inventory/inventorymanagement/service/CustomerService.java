package com.inventory.inventorymanagement.service;

import com.inventory.inventorymanagement.dto.CustomerDTO;
import com.inventory.inventorymanagement.dto.SupplierDTO;
import com.inventory.inventorymanagement.entity.Customer;

import java.util.List;

public interface CustomerService {

    CustomerDTO save (CustomerDTO customerDTO);

    List<CustomerDTO> findAll();

    CustomerDTO findById(int theId);

    void deleteById(int theId);
}
