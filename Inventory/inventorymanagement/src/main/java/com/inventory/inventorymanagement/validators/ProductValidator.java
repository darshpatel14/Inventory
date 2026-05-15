package com.inventory.inventorymanagement.validators;

import com.inventory.inventorymanagement.dao.ProductRepository;
import com.inventory.inventorymanagement.dto.ProductDTO;
import com.inventory.inventorymanagement.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class ProductValidator {

    private ProductRepository productRepository;

    public ProductValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void validateProduct(ProductDTO productDTO){

        if (productDTO == null) {
            throw new BadRequestException("Product data must not be null");
        }

//        //  Product Name validation
//        if (productDTO.getProductName() == null || productDTO.getProductName().trim().isEmpty()) {
//            throw new RuntimeException("Product name is required");
//        }
//
//        if (productDTO.getProductName().length() < 2) {
//            throw new RuntimeException("Product name must be at least 2 characters");
//        }
//
//        //  Product Description validation
//        if (productDTO.getProductDescription() == null || productDTO.getProductDescription().trim().isEmpty()) {
//            throw new RuntimeException("Product description is required");
//        }
//
//        //  Price validation
//        if (productDTO.getPrice() == null) {
//            throw new RuntimeException("Price is required");
//        }
//
//        if (productDTO.getPrice() <= 0) {
//            throw new RuntimeException("Price must be greater than 0");
//        }
//
//        //  Stock validation
//        if (productDTO.getStock() == null) {
//            throw new RuntimeException("Stock is required");
//        }
//
//        if (productDTO.getStock() < 0) {
//            throw new RuntimeException("Stock cannot be negative");
//        }
//
//        //  Category validation
//        if (productDTO.getCategoryId() == null) {
//            throw new RuntimeException("Category is required");
//        }
//
//        //  Supplier validation (optional but recommended)
//        if (productDTO.getSupplierIds() != null && productDTO.getSupplierIds().isEmpty()) {
//            throw new RuntimeException("Supplier list cannot be empty if provided");
//        }

        //  Duplicate product name check (optional but important)
        if (productRepository.existsByProductNameIgnoreCase(productDTO.getProductName())) {
            throw new BadRequestException("Product with this name already exists");
        }
    }

}
