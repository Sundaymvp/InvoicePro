package com.sundaymvp.invoice_api.service;

import com.sundaymvp.invoice_api.dto.request.ProductRequest;
import com.sundaymvp.invoice_api.dto.response.ProductResponse;
import com.sundaymvp.invoice_api.entity.Product;
import com.sundaymvp.invoice_api.exception.ResourceNotFoundException;
import com.sundaymvp.invoice_api.mapper.ProductMapper;
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

    public ProductResponse getProductById(Long id) {

        Objects.requireNonNull(id, "Product id must not be null");

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return ProductMapper.toResponse(product);
    }

    public Product saveProduct(ProductRequest request) {

        Objects.requireNonNull(request, "Product request must not be null");

        Product product = ProductMapper.toEntity(request);

        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductRequest request) {

        Objects.requireNonNull(id, "Product id must not be null");
        Objects.requireNonNull(request, "Product request must not be null");

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());
        product.setSku(request.getSku());
        product.setBarcode(request.getBarcode());
        product.setCostPrice(request.getCostPrice());
        product.setSellingPrice(request.getSellingPrice());
        product.setQuantity(request.getQuantity());
        product.setUnit(request.getUnit());
        product.setStatus(request.getStatus());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {

        Objects.requireNonNull(id, "Product id must not be null");

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        productRepository.delete(product);
    }
}