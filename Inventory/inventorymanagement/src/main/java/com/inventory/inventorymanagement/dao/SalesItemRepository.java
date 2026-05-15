package com.inventory.inventorymanagement.dao;

import com.inventory.inventorymanagement.entity.SalesItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesItemRepository extends JpaRepository<SalesItem, Integer> {
}
