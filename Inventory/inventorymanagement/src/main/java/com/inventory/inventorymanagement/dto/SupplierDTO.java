package com.inventory.inventorymanagement.dto;

import com.inventory.inventorymanagement.entity.Product;
import com.inventory.inventorymanagement.entity.Purchase;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class SupplierDTO {


    private Integer supplierId;

    @NotBlank(message = "Supplier name is required")
    @Size(min = 2, max = 100, message = "Supplier name must be between 2 and 100 characters")
    private String supplierName;

    @NotBlank(message = "Contact person is required")
    @Size(min = 2, max = 100, message = "Contact person must be between 2 and 100 characters")
    private String contactPerson;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phone;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;


    @NotBlank(message = "GST number is required")
    @Pattern(
            regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$",
            message = "Invalid GST number format"
    )
    private String gstNo;


    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "City is required")
    private String city;


//    @NotEmpty(message = "At least one product must be selected")
//    private List<Integer> productIds;


//    private List<PurchaseDTO> purchases;
//    @OneToMany(mappedBy = "supplier")
//    private List<Purchase> purchases;





}
