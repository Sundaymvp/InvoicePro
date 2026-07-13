package com.sundaymvp.invoice_api.repository;

import com.sundaymvp.invoice_api.entity.Payment;
import com.sundaymvp.invoice_api.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
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
     * Count payments by status.
     */
    Long countByStatus(PaymentStatus status);

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

    /**
     * Get payments within a date range.
     */
    List<Payment> findByPaymentDateBetween(
            LocalDate startDate,
            LocalDate endDate);

    /**
     * Calculate total payments within a date range.
     */
    @Query("""
            SELECT COALESCE(SUM(p.amount), 0)
            FROM Payment p
            WHERE p.paymentDate BETWEEN :startDate AND :endDate
            """)
    Double calculatePaymentsBetween(
            LocalDate startDate,
            LocalDate endDate);

    /**
     * Calculate total payments for a specific month and year.
     */
    @Query("""
            SELECT COALESCE(SUM(p.amount), 0)
            FROM Payment p
            WHERE YEAR(p.paymentDate) = :year
              AND MONTH(p.paymentDate) = :month
            """)
    Double calculateMonthlyPayments(
            Integer year,
            Integer month);

    /**
     * Count payments by status within a date range.
     */
    Long countByStatusAndPaymentDateBetween(
            PaymentStatus status,
            LocalDate startDate,
            LocalDate endDate);
}