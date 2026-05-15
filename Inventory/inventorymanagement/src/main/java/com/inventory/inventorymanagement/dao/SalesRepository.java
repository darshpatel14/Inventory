package com.inventory.inventorymanagement.dao;

import com.inventory.inventorymanagement.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SalesRepository extends JpaRepository<Sales,Integer> {

    @Query("SELECT SUM(sale.totalSalesAmount) FROM Sales sale")
    Long getTotalSales();

}
