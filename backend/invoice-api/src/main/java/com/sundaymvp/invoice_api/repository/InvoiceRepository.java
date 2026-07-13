package com.sundaymvp.invoice_api.repository;

import com.sundaymvp.invoice_api.dto.response.OutstandingInvoiceResponse;
import com.sundaymvp.invoice_api.entity.Invoice;
import com.sundaymvp.invoice_api.enums.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

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
     * Count invoices within a date range.
     */
    Long countByInvoiceDateBetween(
            LocalDate startDate,
            LocalDate endDate);

    /**
     * Count invoices by status within a date range.
     */
    Long countByStatusAndInvoiceDateBetween(
            InvoiceStatus status,
            LocalDate startDate,
            LocalDate endDate);

    /**
     * Calculate total revenue.
     */
    @Query("""
            SELECT COALESCE(SUM(i.totalAmount), 0)
            FROM Invoice i
            """)
    Double calculateTotalRevenue();

    /**
     * Calculate revenue within a date range.
     */
    @Query("""
            SELECT COALESCE(SUM(i.totalAmount), 0)
            FROM Invoice i
            WHERE i.invoiceDate BETWEEN :startDate AND :endDate
            """)
    Double calculateRevenueBetween(
            LocalDate startDate,
            LocalDate endDate);

        /**
     * Calculate revenue for a specific month and year.
     */
    @Query("""
            SELECT COALESCE(SUM(i.totalAmount), 0)
            FROM Invoice i
            WHERE YEAR(i.invoiceDate) = :year
              AND MONTH(i.invoiceDate) = :month
            """)
    Double calculateMonthlyRevenue(
            Integer year,
            Integer month);

            /**
 * Outstanding invoices report.
 */
@Query("""
    SELECT new com.sundaymvp.invoice_api.dto.response.OutstandingInvoiceResponse(
        i.id,
        i.invoiceNumber,
        i.customer.name,
        i.invoiceDate,
        i.dueDate,
        i.totalAmount,
        COALESCE(SUM(p.amount), 0),
        i.totalAmount - COALESCE(SUM(p.amount), 0),
        i.status
    )
    FROM Invoice i
    LEFT JOIN Payment p
        ON p.invoice.id = i.id
       AND p.status = com.sundaymvp.invoice_api.enums.PaymentStatus.SUCCESS
    WHERE i.status IN (
        com.sundaymvp.invoice_api.enums.InvoiceStatus.UNPAID,
        com.sundaymvp.invoice_api.enums.InvoiceStatus.PARTIALLY_PAID,
        com.sundaymvp.invoice_api.enums.InvoiceStatus.OVERDUE
    )
    GROUP BY
        i.id,
        i.invoiceNumber,
        i.customer.name,
        i.invoiceDate,
        i.dueDate,
        i.totalAmount,
        i.status
    ORDER BY i.dueDate ASC
    """)
List<OutstandingInvoiceResponse> getOutstandingInvoices();

}