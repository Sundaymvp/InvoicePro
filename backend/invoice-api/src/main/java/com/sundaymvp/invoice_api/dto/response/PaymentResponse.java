package com.sundaymvp.invoice_api.dto.response;

import com.sundaymvp.invoice_api.enums.PaymentMethod;
import com.sundaymvp.invoice_api.enums.PaymentStatus;

import java.time.LocalDate;

public class PaymentResponse {

    private Long id;

    private Long invoiceId;

    private String invoiceNumber;

    private LocalDate paymentDate;

    private PaymentMethod paymentMethod;

    private Double amount;

    private String referenceNumber;

    private String notes;

    private PaymentStatus status;

    public PaymentResponse() {
    }

    public PaymentResponse(
            Long id,
            Long invoiceId,
            String invoiceNumber,
            LocalDate paymentDate,
            PaymentMethod paymentMethod,
            Double amount,
            String referenceNumber,
            String notes,
            PaymentStatus status) {

        this.id = id;
        this.invoiceId = invoiceId;
        this.invoiceNumber = invoiceNumber;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.referenceNumber = referenceNumber;
        this.notes = notes;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public Double getAmount() {
        return amount;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public String getNotes() {
        return notes;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}