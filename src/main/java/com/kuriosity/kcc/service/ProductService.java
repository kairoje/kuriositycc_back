package com.kuriosity.kcc.service;

import com.kuriosity.kcc.exception.InformationAlreadyExists;
import com.kuriosity.kcc.exception.InformationNotFound;
import com.kuriosity.kcc.model.Product;
import com.kuriosity.kcc.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class responsible for managing product-related operations, including retrieval,
 * creation, updating, and deletion of products.
 */
@Service
public class ProductService {

    private ProductRepository productRepository;

    /**
     * Setter for injecting the ProductRepository.
     *
     * @param productRepository The repository for product data.
     */
    @Autowired
    public void setProductRepository(ProductRepository productRepository) { this.productRepository = productRepository; }

    /**
     * Retrieves a list of all available products.
     *
     * @return A list of products if available, or throws an exception if no products are found.
     * @throws InformationNotFound If no products are found in the database.
     */
    public List<Product> getProducts() {
        List<Product> productList = productRepository.findAll();
        if (productList.isEmpty()){
            throw new InformationNotFound("No products found");
        } else {
            return productList;
        }
    }

    /**
     * Retrieves a product by its unique identifier.
     *
     * @param productId The ID of the product to retrieve.
     * @return An optional containing the product if found, or throws an exception if the product is not found.
     * @throws InformationNotFound If no product is found with the given ID.
     */
    public Optional<Product> getProduct(Long productId){
        Product product = productRepository.findProductById(productId);
        if (product == null){
            throw new InformationNotFound("Product with id " + productId + " doesn't exist");
        } else {
            return Optional.of(product);
        }
    }

    /**
     * Creates a new product in the system.
     *
     * @param productObject The product to be created.
     * @return The created product if successful, or throws an exception if a product with the same ID already exists.
     * @throws InformationAlreadyExists If a product with the same ID already exists.
     */
    public Product createProduct(Product productObject) {
        Product product = productRepository.findProductById(productObject.getId());
        if (product != null) {
            throw new InformationAlreadyExists("Product: " + product + " already exist");
        } else {
            return productRepository.save(productObject);
        }
    }


    /**
     * Updates an existing product with the specified ID.
     *
     * @param id The ID of the product to update.
     * @param productObject The updated product information.
     * @return The updated product if successful, or throws an exception if the product is not found.
     * @throws InformationNotFound If no product is found with the given ID for updating.
     */
    public Product updateProduct(Long id, Product productObject) {

        Product product = productRepository.findProductById(id);

        if (product == null) {
            throw new InformationNotFound("Product with id " + id + " doesn't exist");
        } else {
            Product updatedProduct = productRepository.findProductById(id);
            updatedProduct.setName(productObject.getName());
            updatedProduct.setPrice(productObject.getPrice());
            updatedProduct.setStock(productObject.getStock());
            updatedProduct.setDescription(productObject.getDescription());
            updatedProduct.setImage(productObject.getImage());
            return productRepository.save(updatedProduct);
        }
    }

    public Optional<Product> deleteProduct(Long productId) {
        Product product = productRepository.findProductById(productId);
        if (product != null){
            productRepository.deleteById(productId);
            return Optional.of(product);
        } else {
            throw new InformationNotFound("Product with id " + productId + " doesn't exist");
        }
    }

}
