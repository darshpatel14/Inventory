package com.inventory.inventorymanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "purchase_item")
public class PurchaseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    // MANY PURCHASE ITEMS IN ONE PURCHASE
    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;


    @Column(name="product_id")
    private Integer productId;


    @Column(name = "quantity")
    private int quantity;


    @Column(name="unit_cost")
    private double unitCost;


    @Column(name="total_cost")
    private double totalCost;


}
