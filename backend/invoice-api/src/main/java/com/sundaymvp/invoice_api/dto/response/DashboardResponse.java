package com.sundaymvp.invoice_api.dto.response;

public class DashboardResponse {

    private Long totalCustomers;
    private Long totalProducts;
    private Long totalInvoices;

    private Double totalRevenue;
    private Double totalPayments;
    private Double outstandingBalance;

    private Long lowStockProducts;

    private Long paidInvoices;
    private Long partiallyPaidInvoices;
    private Long unpaidInvoices;

    public DashboardResponse() {
    }

    public DashboardResponse(
            Long totalCustomers,
            Long totalProducts,
            Long totalInvoices,
            Double totalRevenue,
            Double totalPayments,
            Double outstandingBalance,
            Long lowStockProducts,
            Long paidInvoices,
            Long partiallyPaidInvoices,
            Long unpaidInvoices) {

        this.totalCustomers = totalCustomers;
        this.totalProducts = totalProducts;
        this.totalInvoices = totalInvoices;
        this.totalRevenue = totalRevenue;
        this.totalPayments = totalPayments;
        this.outstandingBalance = outstandingBalance;
        this.lowStockProducts = lowStockProducts;
        this.paidInvoices = paidInvoices;
        this.partiallyPaidInvoices = partiallyPaidInvoices;
        this.unpaidInvoices = unpaidInvoices;
    }

    public Long getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(Long totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public Long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(Long totalProducts) {
        this.totalProducts = totalProducts;
    }

    public Long getTotalInvoices() {
        return totalInvoices;
    }

    public void setTotalInvoices(Long totalInvoices) {
        this.totalInvoices = totalInvoices;
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

    public Long getLowStockProducts() {
        return lowStockProducts;
    }

    public void setLowStockProducts(Long lowStockProducts) {
        this.lowStockProducts = lowStockProducts;
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
}