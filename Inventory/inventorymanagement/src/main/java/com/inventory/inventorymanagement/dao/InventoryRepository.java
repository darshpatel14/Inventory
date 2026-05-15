package com.inventory.inventorymanagement.dao;

import com.inventory.inventorymanagement.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory,Integer> {

    Optional<Inventory> findByProduct_ProductId(Integer productId);

    @Query("""
            SELECT i FROM Inventory i
            WHERE DATE(i.lastUpdated)
            BETWEEN :fromDate AND :toDate
            """)
    List<Inventory> findByDateRange(
            LocalDate fromDate,
            LocalDate toDate
    );

}
