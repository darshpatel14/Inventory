package com.inventory.inventorymanagement.controller;


import com.inventory.inventorymanagement.dto.ProductDTO;
import com.inventory.inventorymanagement.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/inventory")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    // Injecting UserService
    private ProductService productService;

    public ProductController(ProductService theProductService)
    {
        productService = theProductService;
    }




    @GetMapping("/products")
    public List<ProductDTO> getAllProduct(){
        return productService.findAll();
    }

    @GetMapping("/products/{productId}")
    public ProductDTO getProductById(@PathVariable int productId){
        return productService.findById(productId);
    }


    @PostMapping("/products")
    public ProductDTO saveProduct(@Valid @RequestBody ProductDTO productDTO){
        return productService.save(productDTO);
    }

    @DeleteMapping("/products/{productId}")
    public String deleteProduct(@PathVariable int productId){
        productService.deleteById(productId);
        return "Product Deleted Successfully";
    }

}
