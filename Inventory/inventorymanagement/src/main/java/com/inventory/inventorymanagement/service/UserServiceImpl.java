package com.inventory.inventorymanagement.service;

import com.inventory.inventorymanagement.dao.UserRepository;
import com.inventory.inventorymanagement.dto.UserRequestDTO;
import com.inventory.inventorymanagement.dto.UserResponseDTO;
import com.inventory.inventorymanagement.entity.Admin;
import com.inventory.inventorymanagement.entity.Manager;
import com.inventory.inventorymanagement.entity.User;
import com.inventory.inventorymanagement.validators.UserValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service // Spring will register this class as Service Bean (managed by spring)
public class UserServiceImpl implements UserService{


    // variable to access Userrepository methods
    private final UserRepository userRepository;
    private final UserValidator userValidator;

    public UserServiceImpl(UserRepository userRepository, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
    }


    // calling save method of user repository

    @Override
    public UserResponseDTO save(UserRequestDTO userRequestDTO) {

        userValidator.validateUser(userRequestDTO);

        User user;

        //role handling
        if ("ADMIN".equalsIgnoreCase(userRequestDTO.getRole())) {
            user = new Admin();
        } else if ("MANAGER".equalsIgnoreCase(userRequestDTO.getRole())) {
            user = new Manager();
        } else {
            user = new User();
        }

        // mapping DTO → Entity
        user.setUsername(userRequestDTO.getUsername());
        user.setPassword(userRequestDTO.getPassword());
        user.setEmail(userRequestDTO.getEmail());
        user.setPhone(userRequestDTO.getPhone());

        User savedUser = userRepository.save(user);

        // mapping Entity → ResponseDTO
        return mapToResponse(savedUser);    }

    @Override
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO findById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        return mapToResponse(user);
    }

    @Override
    public void deleteById(int id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }


    private UserResponseDTO mapToResponse(User user) {
        return new UserResponseDTO(
                user.getUserid(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone()
        );
    }
}
