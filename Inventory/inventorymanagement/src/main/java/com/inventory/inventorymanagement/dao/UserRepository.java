package com.inventory.inventorymanagement.dao;


import com.inventory.inventorymanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {


        //Spring Data JPA interface.
        //UserRepository inherits and interacts with all database methods.
        //save(), findById(), findAll(), delete()


    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}






