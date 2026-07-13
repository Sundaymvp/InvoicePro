package com.sundaymvp.invoice_api.dto.response;

public class MonthlyRevenueResponse {

    private Integer year;

    private Integer month;

    private Double totalRevenue;

    private Double totalPayments;

    private Double outstandingBalance;

    public MonthlyRevenueResponse() {
    }

    public MonthlyRevenueResponse(
            Integer year,
            Integer month,
            Double totalRevenue,
            Double totalPayments,
            Double outstandingBalance) {

        this.year = year;
        this.month = month;
        this.totalRevenue = totalRevenue;
        this.totalPayments = totalPayments;
        this.outstandingBalance = outstandingBalance;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
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