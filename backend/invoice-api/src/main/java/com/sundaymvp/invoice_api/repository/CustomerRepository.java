package com.sundaymvp.invoice_api.repository;

import com.sundaymvp.invoice_api.entity.Customer;
import com.sundaymvp.invoice_api.enums.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Count customers that have at least one invoice.
     */
    @Query("""
            SELECT COUNT(DISTINCT i.customer.id)
            FROM Invoice i
            """)
    Long countCustomersWithInvoices();

    /**
     * Count customers that have never received an invoice.
     */
    @Query("""
            SELECT COUNT(c)
            FROM Customer c
            WHERE c.id NOT IN (
                SELECT DISTINCT i.customer.id
                FROM Invoice i
            )
            """)
    Long countCustomersWithoutInvoices();

    /**
     * Count customers that have outstanding invoices.
     * (UNPAID or PARTIALLY_PAID)
     */
    @Query("""
            SELECT COUNT(DISTINCT i.customer.id)
            FROM Invoice i
            WHERE i.status IN (
                com.sundaymvp.invoice_api.enums.InvoiceStatus.UNPAID,
                com.sundaymvp.invoice_api.enums.InvoiceStatus.PARTIALLY_PAID
            )
            """)
    Long countCustomersWithOutstandingInvoices();

}