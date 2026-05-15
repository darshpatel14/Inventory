package com.inventory.inventorymanagement.controller;


import com.inventory.inventorymanagement.dto.UserRequestDTO;
import com.inventory.inventorymanagement.dto.UserResponseDTO;
import com.inventory.inventorymanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {                               //Controller handles HTTP requests.

    // Injecting UserService
    private UserService userService;


    public UserController(UserService theUserService)
    {
        userService = theUserService;
    }


    @GetMapping("/users")
    public List<UserResponseDTO> getAllUsers() {
        return userService.findAll();
    }


    @GetMapping("/users/{userId}")
    public UserResponseDTO getUser(@PathVariable int userId) {
        return userService.findById(userId);
    }


    @PostMapping("/users")
    public UserResponseDTO saveUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return userService.save(userRequestDTO);
    }


    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable int userId) {
        userService.deleteById(userId);
        return "User deleted successfully";
    }

}
