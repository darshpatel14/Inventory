package com.inventory.inventorymanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sales")

public class Sales {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // MANY SALES TO ONE CUSTOMER
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


    @Column(name = "sales_Date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate salesDate;


    @Column(name = "amount")
    private double totalSalesAmount;


    @Column(name = "payment_mode")
    private String modeOfPayment;


    // MANY SALES BY ONE USER
    @ManyToOne
    @JoinColumn(name="created_by")
    private User user;


    // ONE SALE HAS MANY SALES ITEMS
    @OneToMany(mappedBy = "sales", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<SalesItem> salesItems = new ArrayList<>();



    public void addItem(SalesItem item)
    {
        salesItems.add(item);
        item.setSales(this);
    }

}
