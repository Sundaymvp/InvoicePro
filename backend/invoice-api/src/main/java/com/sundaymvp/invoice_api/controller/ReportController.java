package com.sundaymvp.invoice_api.controller;

import com.sundaymvp.invoice_api.dto.response.CustomerReportResponse;
import com.sundaymvp.invoice_api.dto.response.InventoryReportResponse;
import com.sundaymvp.invoice_api.dto.response.MonthlyRevenueResponse;
import com.sundaymvp.invoice_api.dto.response.PaymentReportResponse;
import com.sundaymvp.invoice_api.dto.response.RevenueReportResponse;
import com.sundaymvp.invoice_api.dto.response.SalesReportResponse;
import com.sundaymvp.invoice_api.dto.response.TopSellingProductResponse;
import com.sundaymvp.invoice_api.service.ReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Sales Report
     *
     * GET /api/reports/sales
     * GET /api/reports/sales?startDate=2026-07-01&endDate=2026-07-31
     */
    @GetMapping("/sales")
    public SalesReportResponse getSalesReport(

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate) {

        if (startDate != null && endDate != null) {
            return reportService.getSalesReport(startDate, endDate);
        }

        return reportService.getSalesReport();
    }

    /**
     * Revenue Report
     *
     * GET /api/reports/revenue?startDate=2026-07-01&endDate=2026-07-31
     */
    @GetMapping("/revenue")
    public RevenueReportResponse getRevenueReport(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate) {

        return reportService.getRevenueReport(startDate, endDate);
    }

    /**
     * Monthly Revenue Report
     *
     * GET /api/reports/monthly-revenue?year=2026&month=7
     */
    @GetMapping("/monthly-revenue")
    public MonthlyRevenueResponse getMonthlyRevenueReport(

            @RequestParam Integer year,

            @RequestParam Integer month) {

        return reportService.getMonthlyRevenueReport(year, month);
    }

    /**
     * Payment Report
     *
     * GET /api/reports/payments
     */
    @GetMapping("/payments")
    public PaymentReportResponse getPaymentReport() {

        return reportService.getPaymentReport();
    }

    /**
     * Inventory Report
     *
     * GET /api/reports/inventory
     */
    @GetMapping("/inventory")
    public InventoryReportResponse getInventoryReport() {

        return reportService.getInventoryReport();
    }

    /**
     * Customer Report
     *
     * GET /api/reports/customers
     */
    @GetMapping("/customers")
    public CustomerReportResponse getCustomerReport() {
    

        return reportService.getCustomerReport();
    }

        /**
     * Top Selling Products Report
     *
     * GET /api/reports/top-products
     */
    @GetMapping("/top-products")
    public List<TopSellingProductResponse> getTopSellingProducts() {

        return reportService.getTopSellingProducts();
    }
}