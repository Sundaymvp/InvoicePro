package com.sundaymvp.invoice_api.controller;

import com.sundaymvp.invoice_api.dto.request.ProductRequest;
import com.sundaymvp.invoice_api.dto.response.ProductResponse;
import com.sundaymvp.invoice_api.entity.Product;
import com.sundaymvp.invoice_api.service.ProductService;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

/**
 * Get products with pagination and sorting.
 *
 * Examples:
 * GET /api/products
 * GET /api/products?page=1&size=20
 * GET /api/products?page=0&size=10&sort=name,asc
 */
@GetMapping
public Page<Product> getProducts(

        @RequestParam(defaultValue = "0")
        int page,

        @RequestParam(defaultValue = "10")
        int size,

        @RequestParam(defaultValue = "id")
        String sortBy,

        @RequestParam(defaultValue = "asc")
        String direction) {

    return productService.getProducts(
            page,
            size,
            sortBy,
            direction);
}

/**
 * Search products by name.
 *
 * Examples:
 * GET /api/products/search?keyword=laptop
 * GET /api/products/search?keyword=mouse&page=0&size=5
 */
@GetMapping("/search")
public Page<Product> searchProducts(

        @RequestParam String keyword,

        @RequestParam(defaultValue = "0")
        int page,

        @RequestParam(defaultValue = "10")
        int size,

        @RequestParam(defaultValue = "id")
        String sortBy,

        @RequestParam(defaultValue = "asc")
        String direction) {

    return productService.searchProducts(
            keyword,
            page,
            size,
            sortBy,
            direction);
}    

/**
 * Filter products.
 *
 * Examples:
 * GET /api/products/filter
 * GET /api/products/filter?category=Electronics
 * GET /api/products/filter?status=true
 * GET /api/products/filter?minPrice=1000&maxPrice=5000
 * GET /api/products/filter?category=Electronics&status=true
 * GET /api/products/filter?category=Electronics&status=true&minPrice=1000&maxPrice=5000
 */
@GetMapping("/filter")
public Page<Product> filterProducts(

        @RequestParam(required = false)
        String category,

        @RequestParam(required = false)
        Boolean status,

        @RequestParam(required = false)
        Double minPrice,

        @RequestParam(required = false)
        Double maxPrice,

        @RequestParam(defaultValue = "0")
        int page,

        @RequestParam(defaultValue = "10")
        int size,

        @RequestParam(defaultValue = "id")
        String sortBy,

        @RequestParam(defaultValue = "asc")
        String direction) {

    return productService.filterProducts(
            category,
            status,
            minPrice,
            maxPrice,
            page,
            size,
            sortBy,
            direction);
}
    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public Product createProduct(@RequestBody ProductRequest request) {
        return productService.saveProduct(request);
    }

    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest request) {

        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}