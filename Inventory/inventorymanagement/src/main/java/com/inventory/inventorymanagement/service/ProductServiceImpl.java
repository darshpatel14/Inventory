package com.inventory.inventorymanagement.service;

import com.inventory.inventorymanagement.dao.CategoryRepository;
import com.inventory.inventorymanagement.dao.InventoryRepository;
import com.inventory.inventorymanagement.dao.ProductRepository;
import com.inventory.inventorymanagement.dao.SupplierRepository;
import com.inventory.inventorymanagement.dto.ProductDTO;
import com.inventory.inventorymanagement.entity.Category;
import com.inventory.inventorymanagement.entity.Inventory;
import com.inventory.inventorymanagement.entity.Product;
import com.inventory.inventorymanagement.entity.Supplier;
import com.inventory.inventorymanagement.exception.ResourceNotFoundException;
import com.inventory.inventorymanagement.validators.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    private SupplierRepository supplierRepository;
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductValidator productValidator;

    public ProductServiceImpl(ProductRepository theProductRepository,
                              CategoryRepository theCategoryRepository,
                              SupplierRepository theSupplierRepository,
                              InventoryRepository theInventoryRepository)
    {
        productRepository = theProductRepository;
        categoryRepository = theCategoryRepository;
        supplierRepository = theSupplierRepository;
        inventoryRepository = theInventoryRepository;
    }


    // DTO -> ENTITY
    private Product convertDtoToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setProductId(productDTO.getProductId());
        product.setProductName(productDTO.getProductName());
        product.setProductDescription(productDTO.getProductDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());

        // Set Category using ids
        Category category = categoryRepository
                .findById(productDTO.getCategoryId())
                .orElseThrow(()-> new RuntimeException("Category Not Found"));

        product.setCategory(category);


        // SET SUPPLIERS
        if (productDTO.getSupplierIds() != null && !productDTO.getSupplierIds().isEmpty()) {

            List<Supplier> suppliers = productDTO.getSupplierIds()
                    .stream()
                    .map(id -> supplierRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Supplier not found")))
                    .toList();

            product.setSuppliers(suppliers);


            for (Supplier supplier : suppliers) {
                supplier.getProducts().add(product);
            }
        }

        return product;

    }


    // ENTITY -> DTO
    private ProductDTO convertEntityToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(product.getProductId());
        productDTO.setProductName(product.getProductName());
        productDTO.setProductDescription(product.getProductDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setStock(product.getStock());


        // Category -> categoryId
        if(product.getCategory() != null) {
            productDTO.setCategoryId(product.getCategory().getCategoryId());
        }

        // Supplier -> supplierId
        if(product.getSuppliers() != null) {
            productDTO.setSupplierIds(
                    product.getSuppliers()
                            .stream()
                            .map(Supplier::getSupplierId)
                            .collect(Collectors.toList())
            );
        }


        return productDTO;
    }



    @Override
    public ProductDTO save(ProductDTO theProductDTO) {

        productValidator.validateProduct(theProductDTO);

        Product product = convertDtoToEntity(theProductDTO);

        // SAVE PRODUCT FIRST
        Product savedProduct = productRepository.save(product);

        // CREATE INVENTORY ENTRY
        Inventory inventory = new Inventory();

        inventory.setProduct(savedProduct);

        inventory.setStockIn(0);

        inventory.setStockOut(0);

        inventory.setCurrentStock(savedProduct.getStock());

        inventoryRepository.save(inventory);

        return convertEntityToDTO(savedProduct);
    }

    @Override
    public List<ProductDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO findById(int theId) {
        Product product = productRepository.findById(theId)
                .orElseThrow(()-> new ResourceNotFoundException("Product id not found - " + theId));

        return convertEntityToDTO(product);
    }

    @Override
    public void deleteById(int theId) {

        Product product = productRepository.findById(theId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with id: " + theId));

        // REMOVE PRODUCT FROM SUPPLIERS
        for (Supplier supplier : product.getSuppliers()) {
            supplier.getProducts().remove(product);
        }

        product.getSuppliers().clear();

        productRepository.save(product);

        // DELETE PRODUCT
        productRepository.delete(product);
    }

}
