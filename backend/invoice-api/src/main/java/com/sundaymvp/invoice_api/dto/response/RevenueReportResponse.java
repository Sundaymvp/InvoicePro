package com.sundaymvp.invoice_api.dto.response;

import java.time.LocalDate;

public class RevenueReportResponse {

    private LocalDate periodStart;
    private LocalDate periodEnd;

    private Double totalRevenue;
    private Double totalPayments;
    private Double outstandingBalance;

    public RevenueReportResponse() {
    }

    public RevenueReportResponse(
            LocalDate periodStart,
            LocalDate periodEnd,
            Double totalRevenue,
            Double totalPayments,
            Double outstandingBalance) {

        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.totalRevenue = totalRevenue;
        this.totalPayments = totalPayments;
        this.outstandingBalance = outstandingBalance;
    }

    public LocalDate getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(LocalDate periodStart) {
        this.periodStart = periodStart;
    }

    public LocalDate getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(LocalDate periodEnd) {
        this.periodEnd = periodEnd;
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