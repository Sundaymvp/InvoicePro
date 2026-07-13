package com.sundaymvp.invoice_api.dto.response;

public class SalesReportResponse {

    private Long totalInvoices;
    private Long paidInvoices;
    private Long partiallyPaidInvoices;
    private Long unpaidInvoices;

    private Double totalRevenue;
    private Double totalPayments;
    private Double outstandingBalance;

    public SalesReportResponse() {
    }

    public SalesReportResponse(
            Long totalInvoices,
            Long paidInvoices,
            Long partiallyPaidInvoices,
            Long unpaidInvoices,
            Double totalRevenue,
            Double totalPayments,
            Double outstandingBalance) {

        this.totalInvoices = totalInvoices;
        this.paidInvoices = paidInvoices;
        this.partiallyPaidInvoices = partiallyPaidInvoices;
        this.unpaidInvoices = unpaidInvoices;
        this.totalRevenue = totalRevenue;
        this.totalPayments = totalPayments;
        this.outstandingBalance = outstandingBalance;
    }

    public Long getTotalInvoices() {
        return totalInvoices;
    }

    public void setTotalInvoices(Long totalInvoices) {
        this.totalInvoices = totalInvoices;
    }

    public Long getPaidInvoices() {
        return paidInvoices;
    }

    public void setPaidInvoices(Long paidInvoices) {
        this.paidInvoices = paidInvoices;
    }

    public Long getPartiallyPaidInvoices() {
        return partiallyPaidInvoices;
    }

    public void setPartiallyPaidInvoices(Long partiallyPaidInvoices) {
        this.partiallyPaidInvoices = partiallyPaidInvoices;
    }

    public Long getUnpaidInvoices() {
        return unpaidInvoices;
    }

    public void setUnpaidInvoices(Long unpaidInvoices) {
        this.unpaidInvoices = unpaidInvoices;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Double getTotalPayments() {
        return totalPayments;
    }

    public void setTotalPayments(Double totalPayments) {
        this.totalPayments = totalPayments;
    }

    public Double getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(Double outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }
}