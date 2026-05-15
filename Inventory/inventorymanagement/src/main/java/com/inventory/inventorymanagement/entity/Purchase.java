package com.inventory.inventorymanagement.entity;


import com.inventory.inventorymanagement.enums.PurchaseStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int purchaseId;


    // MANY PURCHASE FROM ONE SUPPLIER
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;


    @Column(name = "purchase_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate purchaseDate;


    @Column(name = "total_amount")
    private double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name="purchase_status")
    private PurchaseStatus purchaseStatus;


    // ONE PURCHASE MANY PURCHASE ITEMS
    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<PurchaseItem> purchaseItems = new ArrayList<>();



    public void addItem(PurchaseItem item) {
        purchaseItems.add(item);
        item.setPurchase(this);
    }

}
