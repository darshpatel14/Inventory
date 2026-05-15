package com.inventory.inventorymanagement.service;

import com.inventory.inventorymanagement.dao.CustomerRepository;
import com.inventory.inventorymanagement.dto.CustomerDTO;
import com.inventory.inventorymanagement.entity.Category;
import com.inventory.inventorymanagement.entity.Customer;
import com.inventory.inventorymanagement.exception.ResourceNotFoundException;
import com.inventory.inventorymanagement.validators.CustomerValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService{

    private CustomerRepository customerRepository;
    private CustomerValidator customerValidator;

    public CustomerServiceImpl(CustomerRepository customerRepository,CustomerValidator theCustomerValidator) {
        this.customerRepository = customerRepository;
        this.customerValidator = theCustomerValidator;
    }


    // DTO -> ENTITY
    private Customer convertDtoToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setCustomerName(customerDTO.getCustomerName());
        customer.setPhone(customerDTO.getPhone());
        customer.setEmail(customerDTO.getEmail());
        customer.setState(customerDTO.getState());
        customer.setCity(customerDTO.getCity());

        return customer;
    }


    // ENTITY -> DTO

    private CustomerDTO convertEntityToDto(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setCustomerName(customer.getCustomerName());
        customerDTO.setPhone(customer.getPhone());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setState(customer.getState());
        customerDTO.setCity(customer.getCity());

        return customerDTO;
    }


    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {

        customerValidator.validateCustomer(customerDTO);

        Customer customer = convertDtoToEntity(customerDTO);

        Customer savedCustomer = customerRepository.save(customer);

        return convertEntityToDto(savedCustomer);
    }

    @Override
    public List<CustomerDTO> findAll() {
        return customerRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

    }


    @Override
    public CustomerDTO findById(int theId) {

        Customer customer = customerRepository.findById(theId)
                .orElseThrow(()-> new ResourceNotFoundException("Customer id not found - " + theId));

        return convertEntityToDto(customer);
    }

    @Override
    public void deleteById(int theId) {

        if (!customerRepository.existsById(theId)) {
            throw new ResourceNotFoundException("Customer not found with id: " + theId);
        }

        customerRepository.deleteById(theId);
    }
}
