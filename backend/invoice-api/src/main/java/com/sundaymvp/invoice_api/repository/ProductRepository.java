package com.sundaymvp.invoice_api.repository;

import com.sundaymvp.invoice_api.entity.Company;
import com.sundaymvp.invoice_api.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Count products whose stock quantity is less than or equal to
     * the specified threshold.
     */
    @Query("""
            SELECT COUNT(p)
            FROM Product p
            WHERE p.quantity <= :threshold
            """)
    Long countLowStockProducts(Integer threshold);

    @Query("""
    SELECT COUNT(p)
    FROM Product p
    WHERE p.company = :company
    AND p.quantity <= :quantity
    """)
Long countLowStockProducts(
        Company company,
        Integer quantity);

    /**
     * Count products that are out of stock.
     */
    @Query("""
            SELECT COUNT(p)
            FROM Product p
            WHERE p.quantity = 0
            """)
    Long countOutOfStockProducts();


    /**
     * Calculate the total quantity of all products in stock.
     */
    @Query("""
            SELECT COALESCE(SUM(p.quantity), 0)
            FROM Product p
            """)
    Long calculateTotalStockQuantity();

    /**
     * Calculate the total inventory value.
     * (Quantity × Selling Price)
     */
    @Query("""
            SELECT COALESCE(SUM(p.quantity * p.sellingPrice), 0)
            FROM Product p
            """)
    Double calculateInventoryValue();

    /**
 * Search products by name.
 */
Page<Product> findByNameContainingIgnoreCase(
        String keyword,
        Pageable pageable);

/**
 * Search by category.
 */
Page<Product> findByCategoryContainingIgnoreCase(
        String category,
        Pageable pageable);

/**
 * Filter by status.
 */
Page<Product> findByStatus(
        Boolean status,
        Pageable pageable);

/**
 * Filter by category and status.
 */
Page<Product> findByCategoryContainingIgnoreCaseAndStatus(
        String category,
        Boolean status,
        Pageable pageable);

/**
 * Filter by selling price range.
 */
Page<Product> findBySellingPriceBetween(
        Double minPrice,
        Double maxPrice,
        Pageable pageable);

/**
 * Filter by category and selling price.
 */
Page<Product> findByCategoryContainingIgnoreCaseAndSellingPriceBetween(
        String category,
        Double minPrice,
        Double maxPrice,
        Pageable pageable);

/**
 * Filter by status and selling price.
 */
Page<Product> findByStatusAndSellingPriceBetween(
        Boolean status,
        Double minPrice,
        Double maxPrice,
        Pageable pageable);

/**
 * Filter by category, status and selling price.
 */
Page<Product> findByCategoryContainingIgnoreCaseAndStatusAndSellingPriceBetween(
        String category,
        Boolean status,
        Double minPrice,
        Double maxPrice,
        Pageable pageable);

        
}