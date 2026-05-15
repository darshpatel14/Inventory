package com.inventory.inventorymanagement.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    // defining the fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer productId;


    @Column(name="product_name")
    private String productName;


    @Column(name="product_description")
    private String productDescription;


    @Column(name="price")
    private double price;


    @Column(name="stock")
    private int stock;


    // MANY PRODUCTS <-> ONE CATEGORY
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    // MANY PRODUCTS <-> MANY SUPPLIERS
    @ManyToMany(mappedBy = "products")
    private List<Supplier> suppliers = new ArrayList<>();


    public void addSupplier(Supplier theSupplier)
    {
        if(suppliers == null){
            suppliers = new ArrayList<>();
        }

        suppliers.add(theSupplier);
        theSupplier.addProduct(this);
    }


}
