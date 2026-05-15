package com.inventory.inventorymanagement.dao;

import com.inventory.inventorymanagement.entity.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem,Integer> {

}
