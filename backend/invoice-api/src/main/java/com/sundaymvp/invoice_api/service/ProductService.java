package com.sundaymvp.invoice_api.service;

import com.sundaymvp.invoice_api.dto.request.ProductRequest;
import com.sundaymvp.invoice_api.dto.response.ProductResponse;
import com.sundaymvp.invoice_api.entity.Company;
import com.sundaymvp.invoice_api.entity.Product;
import com.sundaymvp.invoice_api.exception.ResourceNotFoundException;
import com.sundaymvp.invoice_api.mapper.ProductMapper;
import com.sundaymvp.invoice_api.repository.CompanyRepository;
import com.sundaymvp.invoice_api.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("null")
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;

    public ProductService(ProductRepository productRepository, CompanyRepository companyRepository) {
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
    }

    public List<Product> getAllProducts() {
    
        return productRepository.findAll();
    }

    /**
 * Get products with pagination.
 */
public Page<Product> getProducts(
        int page,
        int size) {

    Pageable pageable = PageRequest.of(page, size);

    return productRepository.findAll(pageable);
}

    public ProductResponse getProductById(Long id) {

        Objects.requireNonNull(id, "Product id must not be null");

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return ProductMapper.toResponse(product);
    }

    /**
 * Get paginated and sorted products.
 */
public Page<Product> getProducts(
        int page,
        int size,
        String sortBy,
        String direction) {

    Sort sort = direction.equalsIgnoreCase("desc")
            ? Sort.by(sortBy).descending()
            : Sort.by(sortBy).ascending();

    Pageable pageable = PageRequest.of(page, size, sort);

    return productRepository.findAll(pageable);
}

    public Product saveProduct(ProductRequest request) {

        Objects.requireNonNull(request, "Product request must not be null");
                Company company = companyRepository.findById(1L)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Company not found"));


        Product product = ProductMapper.toEntity(request);
        product.setCompany(company);

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

        // Preserve company relationship
product.setCompany(product.getCompany());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {

        Objects.requireNonNull(id, "Product id must not be null");

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        productRepository.delete(product);
    }

    /**
 * Search products with pagination and sorting.
 */
public Page<Product> searchProducts(
        String keyword,
        int page,
        int size,
        String sortBy,
        String direction) {

    Sort sort = direction.equalsIgnoreCase("desc")
            ? Sort.by(sortBy).descending()
            : Sort.by(sortBy).ascending();

    Pageable pageable = PageRequest.of(
            page,
            size,
            sort);

    return productRepository.findByNameContainingIgnoreCase(
            keyword,
            pageable);
}

/**
 * Filter products.
 */
public Page<Product> filterProducts(

        String category,
        Boolean status,
        Double minPrice,
        Double maxPrice,
        int page,
        int size,
        String sortBy,
        String direction) {

    Sort sort = direction.equalsIgnoreCase("desc")
            ? Sort.by(sortBy).descending()
            : Sort.by(sortBy).ascending();

    Pageable pageable = PageRequest.of(page, size, sort);

    boolean hasCategory =
            category != null && !category.isBlank();

    boolean hasStatus =
            status != null;

    boolean hasPrice =
            minPrice != null && maxPrice != null;

    if (hasCategory && hasStatus && hasPrice) {

        return productRepository
                .findByCategoryContainingIgnoreCaseAndStatusAndSellingPriceBetween(
                        category,
                        status,
                        minPrice,
                        maxPrice,
                        pageable);
    }

    if (hasCategory && hasStatus) {

        return productRepository
                .findByCategoryContainingIgnoreCaseAndStatus(
                        category,
                        status,
                        pageable);
    }

    if (hasCategory && hasPrice) {

        return productRepository
                .findByCategoryContainingIgnoreCaseAndSellingPriceBetween(
                        category,
                        minPrice,
                        maxPrice,
                        pageable);
    }

    if (hasStatus && hasPrice) {

        return productRepository
                .findByStatusAndSellingPriceBetween(
                        status,
                        minPrice,
                        maxPrice,
                        pageable);
    }

    if (hasCategory) {

        return productRepository
                .findByCategoryContainingIgnoreCase(
                        category,
                        pageable);
    }

    if (hasStatus) {

        return productRepository
                .findByStatus(
                        status,
                        pageable);
    }

    if (hasPrice) {

        return productRepository
                .findBySellingPriceBetween(
                        minPrice,
                        maxPrice,
                        pageable);
    }

    return productRepository.findAll(pageable);
}
}