package com.inventory.inventorymanagement.dao;

import com.inventory.inventorymanagement.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    boolean existsByCustomerNameIgnoreCase(String supplierName);

    boolean existsByEmailIgnoreCase(String email);

}
