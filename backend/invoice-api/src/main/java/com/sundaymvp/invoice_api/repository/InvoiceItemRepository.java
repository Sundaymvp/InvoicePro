package com.sundaymvp.invoice_api.repository;

import com.sundaymvp.invoice_api.dto.response.TopSellingProductResponse;
import com.sundaymvp.invoice_api.entity.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {

    /**
     * Get all items belonging to an invoice.
     */
    List<InvoiceItem> findByInvoiceId(Long invoiceId);

    /**
     * Calculate invoice total.
     */
    @Query("""
            SELECT COALESCE(SUM(i.lineTotal), 0)
            FROM InvoiceItem i
            WHERE i.invoice.id = :invoiceId
            """)
    Double calculateInvoiceTotal(Long invoiceId);

    /**
     * Top selling products ranked by quantity sold.
     */
    @Query("""
            SELECT new com.sundaymvp.invoice_api.dto.response.TopSellingProductResponse(
                p.id,
                p.name,
                SUM(i.quantity),
                SUM(i.lineTotal)
            )
            FROM InvoiceItem i
            JOIN i.product p
            GROUP BY p.id, p.name
            ORDER BY SUM(i.quantity) DESC
            """)
    List<TopSellingProductResponse> getTopSellingProducts();

}