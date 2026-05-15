package com.inventory.inventorymanagement.validators;

import com.inventory.inventorymanagement.dao.CustomerRepository;
import com.inventory.inventorymanagement.dto.CustomerDTO;
import com.inventory.inventorymanagement.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerValidator {

    @Autowired
    public CustomerRepository customerRepository;

    public void validateCustomer(CustomerDTO customerDTO){

        if (customerDTO == null) {
            throw new BadRequestException("Customer data cannot be null");
        }

        if (customerRepository.existsByCustomerNameIgnoreCase(customerDTO.getCustomerName())) {
            throw new BadRequestException("Customer with this name already exists");
        }

        if (customerRepository.existsByEmailIgnoreCase(customerDTO.getEmail())) {
            throw new BadRequestException("Customer with this email already exists");
        }


    }
}
