package com.inventory.inventorymanagement.dto;

import com.inventory.inventorymanagement.entity.Sales;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private int userid;
    private String username;
    private String email;
    private String phone;

}
