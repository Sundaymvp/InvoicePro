package com.sundaymvp.invoice_api.dto.request;

import java.time.LocalDate;

public class InvoiceRequest {

    private String invoiceNumber;
    private Long customerId;
    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private Double totalAmount;
    private String status;
    private String notes;

    public InvoiceRequest() {
    }

    public InvoiceRequest(String invoiceNumber,
                          Long customerId,
                          LocalDate invoiceDate,
                          LocalDate dueDate,
                          Double totalAmount,
                          String status,
                          String notes) {

        this.invoiceNumber = invoiceNumber;
        this.customerId = customerId;
        this.invoiceDate = invoiceDate;
        this.dueDate = dueDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.notes = notes;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}