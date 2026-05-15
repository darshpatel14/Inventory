package com.inventory.inventorymanagement.service;

import com.inventory.inventorymanagement.dto.ProductDTO;
import com.inventory.inventorymanagement.entity.Category;
import com.inventory.inventorymanagement.entity.Product;

import java.util.List;

public interface ProductService {

    ProductDTO save(ProductDTO theProductDTO);

    List<ProductDTO> findAll();

    ProductDTO findById(int theId);

    void deleteById(int theId);

}
