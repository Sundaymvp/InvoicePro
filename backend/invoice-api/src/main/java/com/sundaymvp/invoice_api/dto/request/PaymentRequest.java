package com.sundaymvp.invoice_api.dto.request;

import com.sundaymvp.invoice_api.enums.PaymentMethod;
import com.sundaymvp.invoice_api.enums.PaymentStatus;

import java.time.LocalDate;

public class PaymentRequest {

    private Long invoiceId;

    private LocalDate paymentDate;

    private PaymentMethod paymentMethod;

    private Double amount;

    private String referenceNumber;

    private String notes;

    private PaymentStatus status;

    public PaymentRequest() {
    }

    public PaymentRequest(
            Long invoiceId,
            LocalDate paymentDate,
            PaymentMethod paymentMethod,
            Double amount,
            String referenceNumber,
            String notes,
            PaymentStatus status) {

        this.invoiceId = invoiceId;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.referenceNumber = referenceNumber;
        this.notes = notes;
        this.status = status;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}