package com.sundaymvp.invoice_api.repository;

import com.sundaymvp.invoice_api.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * Get all payments belonging to an invoice.
     */
    List<Payment> findByInvoiceId(Long invoiceId);

    /**
     * Check if payment reference already exists.
     */
    boolean existsByReferenceNumber(String referenceNumber);

    /**
     * Find payment by reference number.
     */
    Optional<Payment> findByReferenceNumber(String referenceNumber);

    /**
     * Calculate total amount paid for a specific invoice.
     */
    @Query("""
            SELECT COALESCE(SUM(p.amount), 0)
            FROM Payment p
            WHERE p.invoice.id = :invoiceId
            """)
    Double calculateTotalPaid(Long invoiceId);

    /**
     * Calculate total payments received.
     */
    @Query("""
            SELECT COALESCE(SUM(p.amount), 0)
            FROM Payment p
            """)
    Double calculateTotalPayments();

}