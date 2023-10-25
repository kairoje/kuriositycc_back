package com.kuriosity.kcc.controller;

import com.kuriosity.kcc.model.Product;
import com.kuriosity.kcc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {

    private ProductService productService;
    @Autowired
    public void setProductService(ProductService productService) { this.productService = productService; }

    @GetMapping("/products")
    public List<Product> getProducts() { return productService.getProducts(); }

    @GetMapping("/products/{productId}")
    public Optional<Product> getProduct(@PathVariable(value = "productId") Long productId) {
        return productService.getProduct(productId);
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody Product productObject) {
        return productService.createProduct(productObject);
    }

    @PutMapping("/products/{productId}")
    public Product updateProduct(@PathVariable(value = "productId") Long productId, @RequestBody Product product) {
        return productService.updateProduct(productId, product);
    }

    @DeleteMapping("/products/{productId}")
    public Optional<Product> deleteProduct(@PathVariable(value = "productId") Long productId) {
        return productService.deleteProduct(productId);
    }
}
