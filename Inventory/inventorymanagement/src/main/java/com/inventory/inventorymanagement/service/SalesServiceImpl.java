package com.inventory.inventorymanagement.service;

import com.inventory.inventorymanagement.dao.CustomerRepository;
import com.inventory.inventorymanagement.dao.InventoryRepository;
import com.inventory.inventorymanagement.dao.SalesRepository;
import com.inventory.inventorymanagement.dao.UserRepository;
import com.inventory.inventorymanagement.dto.PurchaseDTO;
import com.inventory.inventorymanagement.dto.PurchaseItemDTO;
import com.inventory.inventorymanagement.dto.SalesDTO;
import com.inventory.inventorymanagement.dto.SalesItemDTO;
import com.inventory.inventorymanagement.entity.*;
import com.inventory.inventorymanagement.exception.InsufficientStockException;
import com.inventory.inventorymanagement.exception.ResourceNotFoundException;
import com.inventory.inventorymanagement.validators.SalesItemValidator;
import com.inventory.inventorymanagement.validators.SalesValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalesServiceImpl implements SalesService{

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SalesItemValidator salesItemValidator;

    @Autowired
    private SalesValidator salesValidator;

    @Autowired
    private InventoryRepository inventoryRepository;



    @Override
    @Transactional
    public SalesDTO saveSales(SalesDTO salesDTO) {

        for (SalesItemDTO item : salesDTO.getSalesItems()) {
            salesItemValidator.validateSalesItem(item);
        }

        // Validate purchase
        salesValidator.validateSales(salesDTO);




        Sales sales = new Sales();

        // CUSTOMER
        Customer customer = customerRepository
                .findById(salesDTO.getCustomerID())
                .orElseThrow(() -> new RuntimeException("Customer Not Found"));
        sales.setCustomer(customer);


        // USER
        User user = userRepository
                .findById(salesDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        sales.setUser(user);

        sales.setSalesDate(salesDTO.getSalesDate());
        sales.setModeOfPayment(salesDTO.getModeOfPayment());

        double totalSalesAmount = 0;

        for (SalesItemDTO dto : salesDTO.getSalesItems()) {



            SalesItem item = new SalesItem();

            item.setProductId(dto.getProductId());
            item.setQuantity(dto.getQuantity());
            item.setUnitPrice(dto.getUnitPrice());

            double total = dto.getQuantity() * dto.getUnitPrice();
            item.setTotalPrice(total);

            sales.addItem(item);

            totalSalesAmount += total;


            Inventory inventory = inventoryRepository
                    .findByProduct_ProductId(dto.getProductId())
                    .orElseThrow(()->new ResourceNotFoundException("Inventory not found for product id : " + dto.getProductId()));


            if (dto.getQuantity() > inventory.getCurrentStock()) {

                throw new InsufficientStockException(
                        "Insufficient stock available. Available stock = "
                                + inventory.getCurrentStock()
                );
            }


            inventory.setStockOut(inventory.getStockOut() + dto.getQuantity());


            inventory.setCurrentStock(inventory.getStockIn() - inventory.getStockOut());


            inventory.setLastUpdated(java.time.LocalDateTime.now());

            inventoryRepository.save(inventory);


        }


        sales.setTotalSalesAmount(totalSalesAmount);

        Sales saved = salesRepository.save(sales);

        salesDTO.setId(saved.getId());
        salesDTO.setTotalSalesAmount(saved.getTotalSalesAmount());

        return salesDTO;
    }


    private SalesDTO mapToDTO(Sales sales) {

        SalesDTO salesDTO = new SalesDTO();

        salesDTO.setId(sales.getId());
        salesDTO.setCustomerID(sales.getCustomer().getId());
        salesDTO.setSalesDate(sales.getSalesDate());
        salesDTO.setTotalSalesAmount(sales.getTotalSalesAmount());
        salesDTO.setModeOfPayment(sales.getModeOfPayment());
        salesDTO.setUserId(sales.getUser().getUserid());


        List<SalesItemDTO> items = sales.getSalesItems()
                .stream()
                .map(item -> {
                    SalesItemDTO itemDTO = new SalesItemDTO();
                    itemDTO.setId(item.getId());
                    itemDTO.setProductId(Integer.valueOf(item.getProductId()));
                    itemDTO.setQuantity(item.getQuantity());
                    itemDTO.setUnitPrice(item.getUnitPrice());
                    itemDTO.setTotalPrice(item.getTotalPrice());
                    return itemDTO;
                })
                .collect(Collectors.toList());

        salesDTO.setSalesItems(items);

        return salesDTO;
    }


    @Override
    public List<SalesDTO> findAll() {
        return salesRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SalesDTO findById(int theId) {
        Sales sales = salesRepository.findById(theId)
                .orElseThrow(() -> new ResourceNotFoundException("Sales id not found - "+ theId));

        return mapToDTO(sales);
    }

    @Override
    public void deleteById(int theId) {

        if (!salesRepository.existsById(theId)) {
            throw new ResourceNotFoundException("Sales not found with id: " + theId);
        }

        salesRepository.deleteById(theId);
    }
}

