package com.inventory.inventorymanagement.dao;

import com.inventory.inventorymanagement.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Integer> {

    Optional<Inventory> findByProduct_ProductId(Integer productId);

    List<Inventory> findByProduct_ProductName(String productName);

    @Query("""
            SELECT i FROM Inventory i
            WHERE DATE(i.lastUpdated)
            BETWEEN :fromDate AND :toDate
            """)
    List<Inventory> findByDateRange(
            LocalDate fromDate,
            LocalDate toDate
    );


    @Query(value = """

            SELECT i.*
        FROM inventory i
        JOIN product p ON i.product_id = p.id
        JOIN category c ON p.category_id = c.category_id
        JOIN product_supplier ps ON ps.product_id = p.id
        JOIN supplier s ON s.supplier_id = ps.supplier_id
        WHERE c.category_name = :categoryName
        AND s.supplier_name = :supplierName
        """, nativeQuery = true)
    List<Inventory> filterByCategoryAndSupplier(
            String categoryName,
            String supplierName

    );
}