package com.sundaymvp.invoice_api.dto.response;

public class CustomerReportResponse {

    private Long totalCustomers;

    private Long customersWithInvoices;

    private Long customersWithoutInvoices;

    private Long customersWithOutstandingInvoices;

    public CustomerReportResponse() {
    }

    public CustomerReportResponse(
            Long totalCustomers,
            Long customersWithInvoices,
            Long customersWithoutInvoices,
            Long customersWithOutstandingInvoices) {

        this.totalCustomers = totalCustomers;
        this.customersWithInvoices = customersWithInvoices;
        this.customersWithoutInvoices = customersWithoutInvoices;
        this.customersWithOutstandingInvoices = customersWithOutstandingInvoices;
    }

    public Long getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(Long totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public Long getCustomersWithInvoices() {
        return customersWithInvoices;
    }

    public void setCustomersWithInvoices(Long customersWithInvoices) {
        this.customersWithInvoices = customersWithInvoices;
    }

    public Long getCustomersWithoutInvoices() {
        return customersWithoutInvoices;
    }

    public void setCustomersWithoutInvoices(Long customersWithoutInvoices) {
        this.customersWithoutInvoices = customersWithoutInvoices;
    }

    public Long getCustomersWithOutstandingInvoices() {
        return customersWithOutstandingInvoices;
    }

    public void setCustomersWithOutstandingInvoices(Long customersWithOutstandingInvoices) {
        this.customersWithOutstandingInvoices = customersWithOutstandingInvoices;
    }
}