package com.inventory.inventorymanagement.dto;

import com.inventory.inventorymanagement.entity.Customer;
import com.inventory.inventorymanagement.entity.SalesItem;
import com.inventory.inventorymanagement.entity.User;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

public class SalesDTO {


    private Integer id;

    @NotNull(message = "Customer is required")
    private Integer customerID;

    @NotNull(message = "Sales Date is required")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate salesDate;


    @Min(value = 0, message = "Amount cannot be less than 0")
    private Double totalSalesAmount;

    @NotBlank(message = "Mode of Payment is required")
    private String modeOfPayment;

    @NotNull(message = "User is required")
    private Integer userId;

    @NotEmpty(message = "At least one Sales item is required")
    @Valid
    private List<SalesItemDTO> salesItems = new ArrayList<>();
}
