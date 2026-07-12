package com.sundaymvp.invoice_api.mapper;

import com.sundaymvp.invoice_api.dto.response.PaymentResponse;
import com.sundaymvp.invoice_api.entity.Payment;

public class PaymentMapper {

    private PaymentMapper() {
    }

    public static PaymentResponse toResponse(Payment payment) {

        return new PaymentResponse(
                payment.getId(),

                payment.getInvoice().getId(),
                payment.getInvoice().getInvoiceNumber(),

                payment.getPaymentDate(),
                payment.getPaymentMethod(),
                payment.getAmount(),
                payment.getReferenceNumber(),
                payment.getNotes(),
                payment.getStatus()
        );
    }
}