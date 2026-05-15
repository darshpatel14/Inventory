package com.inventory.inventorymanagement.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sales_item")
public class SalesItem {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    // MANY SALES ITEM IN ONE SALE
    @ManyToOne
    @JoinColumn(name = "sales_id")
    private Sales sales;


    @Column(name="product_id")
    private Integer productId;


    @Column(name = "quantity")
    private int quantity;


    @Column(name="unit_price")
    private double unitPrice;


    @Column(name="total_price")
    private double totalPrice;




}
