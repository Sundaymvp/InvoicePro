package com.sundaymvp.invoice_api.dto.response;

import com.sundaymvp.invoice_api.enums.InvoiceStatus;

import java.time.LocalDate;

public class OutstandingInvoiceResponse {

    private Long invoiceId;

    private String invoiceNumber;

    private String customerName;

    private LocalDate invoiceDate;

    private LocalDate dueDate;

    private Double totalAmount;

    private Double amountPaid;

    private Double outstandingBalance;

    private InvoiceStatus status;

    public OutstandingInvoiceResponse() {
    }

    public OutstandingInvoiceResponse(
            Long invoiceId,
            String invoiceNumber,
            String customerName,
            LocalDate invoiceDate,
            LocalDate dueDate,
            Double totalAmount,
            Double amountPaid,
            Double outstandingBalance,
            InvoiceStatus status) {

        this.invoiceId = invoiceId;
        this.invoiceNumber = invoiceNumber;
        this.customerName = customerName;
        this.invoiceDate = invoiceDate;
        this.dueDate = dueDate;
        this.totalAmount = totalAmount;
        this.amountPaid = amountPaid;
        this.outstandingBalance = outstandingBalance;
        this.status = status;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Double getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(Double outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }
}