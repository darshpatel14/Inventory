package com.inventory.inventorymanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="category")
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;


    @Column(name = "category_name")
    private String categoryName;


    @Column(name = "description")
    private String description;


    @Column(name = "created_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdDate;


    @PrePersist
    public void setCreatedDateAutomatically() {
        this.createdDate = LocalDate.now();
    }



    // ONE CATEGORY <-> MANY PRODUCTS
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<Product> products;


}
