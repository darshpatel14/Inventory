package com.inventory.inventorymanagement.service;

import com.inventory.inventorymanagement.dao.ProductRepository;
import com.inventory.inventorymanagement.dao.SupplierRepository;
import com.inventory.inventorymanagement.dto.SupplierDTO;
import com.inventory.inventorymanagement.entity.Product;
import com.inventory.inventorymanagement.entity.Supplier;
import com.inventory.inventorymanagement.exception.ResourceNotFoundException;
import com.inventory.inventorymanagement.validators.SupplierValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {


    private SupplierRepository supplierRepository;
    private ProductRepository productRepository;
    private SupplierValidator supplierValidator;

    public SupplierServiceImpl(SupplierRepository theSupplierRepository,ProductRepository theProductRepository,SupplierValidator theSupplierValidator) {
        supplierRepository = theSupplierRepository;
        productRepository = theProductRepository;
        supplierValidator = theSupplierValidator;
    }

    // DTO -> Entity
    private Supplier convertDtoToEntity(SupplierDTO supplierDTO) {
        Supplier supplier = new Supplier();
        supplier.setSupplierId(supplierDTO.getSupplierId());
        supplier.setSupplierName(supplierDTO.getSupplierName());
        supplier.setContactPerson(supplierDTO.getContactPerson());
        supplier.setPhone(supplierDTO.getPhone());
        supplier.setEmail(supplierDTO.getEmail());
        supplier.setGstNo(supplierDTO.getGstNo());
        supplier.setState(supplierDTO.getState());
        supplier.setCity(supplierDTO.getCity());

//        if (supplierDTO.getProductIds() != null) {
//            List<Product> products = productRepository.findAllById(supplierDTO.getProductIds());
//
//            for (Product product : products) {
//                product.getSuppliers().add(supplier); // maintain both sides
//            }
//
//            supplier.setProducts(products);
//        }

        return supplier;

    }


    // Entity -> DTO

    private SupplierDTO convertEntityToDto(Supplier supplier) {
        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setSupplierId(supplier.getSupplierId());
        supplierDTO.setSupplierName(supplier.getSupplierName());
        supplierDTO.setContactPerson(supplier.getContactPerson());
        supplierDTO.setPhone(supplier.getPhone());
        supplierDTO.setEmail(supplier.getEmail());
        supplierDTO.setGstNo(supplier.getGstNo());
        supplierDTO.setState(supplier.getState());
        supplierDTO.setCity(supplier.getCity());

        return supplierDTO;
    }


    @Override
    public SupplierDTO save(SupplierDTO supplierDTO) {

        supplierValidator.validateSupplier(supplierDTO);

        Supplier supplier = convertDtoToEntity(supplierDTO);

        Supplier savedSupplier = supplierRepository.save(supplier);

        return convertEntityToDto(savedSupplier);
    }

    @Override
    public List<SupplierDTO> findAll() {
        return supplierRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }


    @Override
    public SupplierDTO findById(int theId) {
        Optional<Supplier> result = supplierRepository.findById(theId);

        Supplier supplier = null;

        if (result.isPresent()) {
            supplier = result.get();
        } else {
            throw new RuntimeException("Did not find supplier id - " + theId);
        }

        return convertEntityToDto(supplier);

    }


    @Override
    public void deleteById(int theId) {

        if (!supplierRepository.existsById(theId)) {
            throw new ResourceNotFoundException("Supplier not found with id: " + theId);
        }

        supplierRepository.deleteById(theId);
    }


}

