package com.inventory.inventorymanagement.controller;

import com.inventory.inventorymanagement.dto.CustomerDTO;
import com.inventory.inventorymanagement.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/inventory")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }



    @GetMapping("/customers")
    public List<CustomerDTO> getAllCustomers(){
        return customerService.findAll();
    }

    @GetMapping("/customers/{customerId}")
    public CustomerDTO getCustomerById(@PathVariable int customerId){
        return customerService.findById(customerId);
    }


    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@Valid @RequestBody CustomerDTO customerDTO){
        System.out.println("Controller Hit");
        return customerService.save(customerDTO);
    }

    @DeleteMapping("/customers/{customerId}")
    public String deleteCustomer(@PathVariable int customerId){
        customerService.deleteById(customerId);
        return "Customer Deleted Successfully";
    }

}
