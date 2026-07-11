package com.sundaymvp.invoice_api.mapper;

import com.sundaymvp.invoice_api.dto.response.InvoiceItemResponse;
import com.sundaymvp.invoice_api.entity.InvoiceItem;

public class InvoiceItemMapper {

    private InvoiceItemMapper() {
    }

    public static InvoiceItemResponse toResponse(InvoiceItem invoiceItem) {

        return new InvoiceItemResponse(
                invoiceItem.getId(),
                invoiceItem.getInvoice().getId(),
                invoiceItem.getProduct().getId(),
                invoiceItem.getProduct().getName(),
                invoiceItem.getQuantity(),
                invoiceItem.getUnitPrice(),
                invoiceItem.getDiscount(),
                invoiceItem.getTax(),
                invoiceItem.getLineTotal()
        );
    }
}