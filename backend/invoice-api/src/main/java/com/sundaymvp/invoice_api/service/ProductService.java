package com.sundaymvp.invoice_api.service;

import com.sundaymvp.invoice_api.entity.Product;
import com.sundaymvp.invoice_api.exception.ResourceNotFoundException;
import com.sundaymvp.invoice_api.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {

        Objects.requireNonNull(id, "Product id must not be null");

        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public Product saveProduct(Product product) {

        Objects.requireNonNull(product, "Product must not be null");

        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {

        Objects.requireNonNull(id, "Product id must not be null");
        Objects.requireNonNull(updatedProduct, "Updated product must not be null");

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setCategory(updatedProduct.getCategory());
        product.setSku(updatedProduct.getSku());
        product.setBarcode(updatedProduct.getBarcode());
        product.setCostPrice(updatedProduct.getCostPrice());
        product.setSellingPrice(updatedProduct.getSellingPrice());
        product.setQuantity(updatedProduct.getQuantity());
        product.setUnit(updatedProduct.getUnit());
        product.setStatus(updatedProduct.getStatus());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {

        Objects.requireNonNull(id, "Product id must not be null");

        productRepository.deleteById(id);
    }
}