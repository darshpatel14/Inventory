package com.inventory.inventorymanagement.service;

import com.inventory.inventorymanagement.dto.UserRequestDTO;
import com.inventory.inventorymanagement.dto.UserResponseDTO;

import java.util.List;


public interface UserService {
//    Service layer contains business logic.

    UserResponseDTO save(UserRequestDTO userRequestDTO);

    List<UserResponseDTO> findAll();

    UserResponseDTO findById(int id);

    void deleteById(int id);

}

