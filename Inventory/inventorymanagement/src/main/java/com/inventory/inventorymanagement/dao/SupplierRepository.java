package com.inventory.inventorymanagement.dao;

import com.inventory.inventorymanagement.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    @Query("SELECT COUNT(sup) FROM Supplier sup")
    Long getTotalSuppliers();

    boolean existsBySupplierNameIgnoreCase(String supplierName);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByGstNo(String gstNo);

}
