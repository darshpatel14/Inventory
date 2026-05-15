package com.inventory.inventorymanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // MANY INVENTORY -> ONE PRODUCT
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "stock_in")
    private Integer stockIn = 0;

    @Column(name = "stock_out")
    private Integer stockOut = 0;

    @Column(name = "current_stock")
    private Integer currentStock = 0;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @PrePersist
    @PreUpdate
    public void updateTime() {
        lastUpdated = LocalDateTime.now();
    }
}