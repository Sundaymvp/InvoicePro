package com.sundaymvp.invoice_api.dto.response;

public class PaymentReportResponse {

    private Long totalPayments;
    private Long successfulPayments;
    private Long pendingPayments;
    private Long failedPayments;
    private Long refundedPayments;

    private Double totalAmount;

    public PaymentReportResponse() {
    }

    public PaymentReportResponse(
            Long totalPayments,
            Long successfulPayments,
            Long pendingPayments,
            Long failedPayments,
            Long refundedPayments,
            Double totalAmount) {

        this.totalPayments = totalPayments;
        this.successfulPayments = successfulPayments;
        this.pendingPayments = pendingPayments;
        this.failedPayments = failedPayments;
        this.refundedPayments = refundedPayments;
        this.totalAmount = totalAmount;
    }

    public Long getTotalPayments() {
        return totalPayments;
    }

    public void setTotalPayments(Long totalPayments) {
        this.totalPayments = totalPayments;
    }

    public Long getSuccessfulPayments() {
        return successfulPayments;
    }

    public void setSuccessfulPayments(Long successfulPayments) {
        this.successfulPayments = successfulPayments;
    }

    public Long getPendingPayments() {
        return pendingPayments;
    }

    public void setPendingPayments(Long pendingPayments) {
        this.pendingPayments = pendingPayments;
    }

    public Long getFailedPayments() {
        return failedPayments;
    }

    public void setFailedPayments(Long failedPayments) {
        this.failedPayments = failedPayments;
    }

    public Long getRefundedPayments() {
        return refundedPayments;
    }

    public void setRefundedPayments(Long refundedPayments) {
        this.refundedPayments = refundedPayments;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}