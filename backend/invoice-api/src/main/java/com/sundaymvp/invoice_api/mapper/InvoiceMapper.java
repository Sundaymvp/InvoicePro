package com.sundaymvp.invoice_api.mapper;

import com.sundaymvp.invoice_api.dto.response.InvoiceResponse;
import com.sundaymvp.invoice_api.entity.Invoice;

public class InvoiceMapper {

    private InvoiceMapper() {
    }

    public static InvoiceResponse toResponse(Invoice invoice) {

        return new InvoiceResponse(
                invoice.getId(),
                invoice.getInvoiceNumber(),

                invoice.getCompany().getId(),
                invoice.getCompany().getCompanyName(),

                invoice.getCustomer().getId(),
                invoice.getCustomer().getName(),

                invoice.getInvoiceDate(),
                invoice.getDueDate(),
                invoice.getTotalAmount(),
                invoice.getStatus(),
                invoice.getNotes()
        );
    }
}