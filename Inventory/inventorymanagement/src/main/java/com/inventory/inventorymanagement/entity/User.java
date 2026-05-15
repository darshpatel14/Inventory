package com.inventory.inventorymanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity                // tell spring its a Entity Spring will map it to a database table.
@Table(name="users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name="role",
        discriminatorType = DiscriminatorType.STRING
)
@Builder
public class User {

    // define fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int userid;


    @NotBlank(message = "Please enter username")
    @Column(name="username")
    private String username;


    @NotBlank(message = "Please enter valid password")
    @Column(name="password")
    private String password;


    @NotBlank(message = "Please enter email")
    @Column(name="email")
    private String email;



    @NotBlank(message = "Please enter mobile number")
    @Column(name="phone")
    private String phone;


    
    @OneToMany(mappedBy = "user")
    private List<Sales> sales;


}


