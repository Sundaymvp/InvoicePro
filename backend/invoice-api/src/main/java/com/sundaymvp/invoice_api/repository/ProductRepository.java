package com.sundaymvp.invoice_api.repository;

import com.sundaymvp.invoice_api.entity.Product;
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

}