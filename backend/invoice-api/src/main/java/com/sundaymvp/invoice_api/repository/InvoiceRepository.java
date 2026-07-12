package com.sundaymvp.invoice_api.repository;

import com.sundaymvp.invoice_api.entity.Invoice;
import com.sundaymvp.invoice_api.enums.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    /**
     * Check if invoice number already exists.
     */
    boolean existsByInvoiceNumber(String invoiceNumber);

    /**
     * Count invoices by status.
     */
    Long countByStatus(InvoiceStatus status);

    /**
     * Calculate total revenue from all invoices.
     */
    @Query("""
            SELECT COALESCE(SUM(i.totalAmount), 0)
            FROM Invoice i
            """)
    Double calculateTotalRevenue();

}