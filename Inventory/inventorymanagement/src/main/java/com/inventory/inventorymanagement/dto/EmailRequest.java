package com.inventory.inventorymanagement.dto;

import lombok.Data;

@Data
public class EmailRequest {

    private String email;

    private String type;

    private String fromDate;

    private String toDate;
}
