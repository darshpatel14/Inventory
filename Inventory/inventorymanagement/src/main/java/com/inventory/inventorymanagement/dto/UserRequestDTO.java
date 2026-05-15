package com.inventory.inventorymanagement.dto;


import com.inventory.inventorymanagement.entity.Sales;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {


    @NotBlank(message = "Please enter username")
    private String username;

    @NotBlank(message = "Please enter password")
    private String password;

    @NotBlank(message = "Please enter email")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Please enter phone")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phone;

    private String role;

    private List<Sales> sales;
}
