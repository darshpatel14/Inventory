package com.inventory.inventorymanagement.dao;

import com.inventory.inventorymanagement.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

    @Query("SELECT SUM(purchase.totalAmount) FROM Purchase purchase")
    Long getTotalPurchase();


}
