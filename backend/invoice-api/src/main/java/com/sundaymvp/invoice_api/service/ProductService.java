package com.sundaymvp.invoice_api.service;

import com.sundaymvp.invoice_api.dto.request.ProductRequest;
import com.sundaymvp.invoice_api.dto.response.ProductResponse;
import com.sundaymvp.invoice_api.entity.Company;
import com.sundaymvp.invoice_api.entity.Product;
import com.sundaymvp.invoice_api.exception.ResourceNotFoundException;
import com.sundaymvp.invoice_api.mapper.ProductMapper;
import com.sundaymvp.invoice_api.repository.CompanyRepository;
import com.sundaymvp.invoice_api.repository.ProductRepository;
import com.sundaymvp.invoice_api.audit.Audit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("null")
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final FileStorageService fileStorageService;

    public ProductService(ProductRepository productRepository,
                          CompanyRepository companyRepository,
                          FileStorageService fileStorageService) {
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
        this.fileStorageService = fileStorageService;
    }

    /**
     * Get all products.
     */
    public List<ProductResponse> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    /**
     * Get products with pagination.
     */
    public Page<ProductResponse> getProducts(
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);

        return productRepository.findAll(pageable)
                .map(ProductMapper::toResponse);
    }

    /**
     * Get product by id.
     */
    public ProductResponse getProductById(Long id) {

        Objects.requireNonNull(id, "Product id must not be null");

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        return ProductMapper.toResponse(product);
    }

    /**
     * Get paginated and sorted products.
     */
    public Page<ProductResponse> getProducts(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository.findAll(pageable)
                .map(ProductMapper::toResponse);
    }

    /**
     * Save product.
     */
    @Audit(
        module = "Product",
        action = "CREATE"
)
    public ProductResponse saveProduct(ProductRequest request) {

        Objects.requireNonNull(request, "Product request must not be null");

        Company company = companyRepository.findById(1L)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found"));

        Product product = ProductMapper.toEntity(request);
        product.setCompany(company);

        Product savedProduct = productRepository.save(product);

        return ProductMapper.toResponse(savedProduct);
    }

    /**
     * Update product.
     */
    @Audit(
        module = "Product",
        action = "UPDATE"
)
    public ProductResponse updateProduct(Long id,
                                         ProductRequest request) {

        Objects.requireNonNull(id, "Product id must not be null");
        Objects.requireNonNull(request, "Product request must not be null");

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

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

        Product updatedProduct = productRepository.save(product);

        return ProductMapper.toResponse(updatedProduct);
    }

    /**
 * Upload product image.
 */
@Audit(
        module = "Product",
        action = "UPLOAD IMAGE"
)
public ProductResponse uploadProductImage(
        Long productId,
        MultipartFile file) {

    Objects.requireNonNull(productId, "Product id must not be null");
    Objects.requireNonNull(file, "File must not be null");

    Product product = productRepository.findById(productId)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Product not found"));

    // Delete old image
    if (product.getImage() != null
            && !product.getImage().isBlank()) {

        fileStorageService.deleteFile(
                "product-images",
                product.getImage());
    }

    // Upload new image
    String fileName = fileStorageService.uploadFile(
            file,
            "product-images");

    product.setImage(fileName);

    Product updatedProduct = productRepository.save(product);

    return ProductMapper.toResponse(updatedProduct);
}

    /**
     * Delete product.
     */
    @Audit(
        module = "Product",
        action = "DELETE"
)
    public void deleteProduct(Long id) {

        Objects.requireNonNull(id, "Product id must not be null");

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        productRepository.delete(product);
    }

    /**
     * Search products.
     */
    public Page<ProductResponse> searchProducts(
            String keyword,
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository.findByNameContainingIgnoreCase(
                        keyword,
                        pageable)
                .map(ProductMapper::toResponse);
    }

    /**
     * Filter products.
     */
    public Page<ProductResponse> filterProducts(

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
                            pageable)
                    .map(ProductMapper::toResponse);
        }

        if (hasCategory && hasStatus) {

            return productRepository
                    .findByCategoryContainingIgnoreCaseAndStatus(
                            category,
                            status,
                            pageable)
                    .map(ProductMapper::toResponse);
        }

        if (hasCategory && hasPrice) {

            return productRepository
                    .findByCategoryContainingIgnoreCaseAndSellingPriceBetween(
                            category,
                            minPrice,
                            maxPrice,
                            pageable)
                    .map(ProductMapper::toResponse);
        }

        if (hasStatus && hasPrice) {

            return productRepository
                    .findByStatusAndSellingPriceBetween(
                            status,
                            minPrice,
                            maxPrice,
                            pageable)
                    .map(ProductMapper::toResponse);
        }

        if (hasCategory) {

            return productRepository
                    .findByCategoryContainingIgnoreCase(
                            category,
                            pageable)
                    .map(ProductMapper::toResponse);
        }

        if (hasStatus) {

            return productRepository
                    .findByStatus(
                            status,
                            pageable)
                    .map(ProductMapper::toResponse);
        }

        if (hasPrice) {

            return productRepository
                    .findBySellingPriceBetween(
                            minPrice,
                            maxPrice,
                            pageable)
                    .map(ProductMapper::toResponse);
        }

        return productRepository.findAll(pageable)
                .map(ProductMapper::toResponse);
    }
}