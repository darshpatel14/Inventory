package com.inventory.inventorymanagement.validators;


import com.inventory.inventorymanagement.dao.UserRepository;
import com.inventory.inventorymanagement.dto.UserRequestDTO;
import com.inventory.inventorymanagement.dto.UserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {


    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateUser(UserRequestDTO userRequestDTO){

        // Role validation
        if (userRequestDTO.getRole() == null ||
                !(userRequestDTO.getRole().equalsIgnoreCase("ADMIN") ||
                        userRequestDTO.getRole().equalsIgnoreCase("MANAGER") ||
                        userRequestDTO.getRole().equalsIgnoreCase("USER"))) {

            throw new RuntimeException("Invalid role. Allowed: ADMIN, MANAGER, USER");
        }

        //  Password strength
        if (userRequestDTO.getPassword().length() < 6) {
            throw new RuntimeException("Password must be at least 6 characters");
        }

        // Username already exists (DB check → business logic)
        if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // Email already exists (optional but recommended)
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

    }
}
