package com.sundaymvp.invoice_api.repository;

import com.sundaymvp.invoice_api.entity.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {

    List<InvoiceItem> findByInvoiceId(Long invoiceId);

    @Query("""
            SELECT COALESCE(SUM(i.lineTotal), 0)
            FROM InvoiceItem i
            WHERE i.invoice.id = :invoiceId
            """)
    Double calculateInvoiceTotal(Long invoiceId);

}