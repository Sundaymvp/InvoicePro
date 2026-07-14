package com.sundaymvp.invoice_api.service;

import com.sundaymvp.invoice_api.dto.response.CustomerReportResponse;
import com.sundaymvp.invoice_api.dto.response.InventoryReportResponse;
import com.sundaymvp.invoice_api.dto.response.MonthlyRevenueResponse;
import com.sundaymvp.invoice_api.dto.response.PaymentReportResponse;
import com.sundaymvp.invoice_api.dto.response.RevenueReportResponse;
import com.sundaymvp.invoice_api.dto.response.SalesReportResponse;
import com.sundaymvp.invoice_api.dto.response.TopSellingProductResponse;
import com.sundaymvp.invoice_api.enums.InvoiceStatus;
import com.sundaymvp.invoice_api.enums.PaymentStatus;
import com.sundaymvp.invoice_api.repository.CustomerRepository;
import com.sundaymvp.invoice_api.repository.InvoiceItemRepository;
import com.sundaymvp.invoice_api.repository.InvoiceRepository;
import com.sundaymvp.invoice_api.repository.PaymentRepository;
import com.sundaymvp.invoice_api.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReportService {

    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final InvoiceItemRepository invoiceItemRepository;

    public ReportService(
            InvoiceRepository invoiceRepository,
            PaymentRepository paymentRepository,
            ProductRepository productRepository,
            CustomerRepository customerRepository,
            InvoiceItemRepository invoiceItemRepository) {

        this.invoiceRepository = invoiceRepository;
        this.paymentRepository = paymentRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.invoiceItemRepository = invoiceItemRepository;
    }

    /**
     * Sales Report Summary (All Time)
     */
    public SalesReportResponse getSalesReport() {

        Long totalInvoices = invoiceRepository.count();

        Long paidInvoices =
                invoiceRepository.countByStatus(InvoiceStatus.PAID);

        Long partiallyPaidInvoices =
                invoiceRepository.countByStatus(InvoiceStatus.PARTIALLY_PAID);

        Long unpaidInvoices =
                invoiceRepository.countByStatus(InvoiceStatus.UNPAID);

        Double totalRevenue =
                invoiceRepository.calculateTotalRevenue();

        Double totalPayments =
                paymentRepository.calculateTotalPayments();

        Double outstandingBalance = totalRevenue - totalPayments;

        return new SalesReportResponse(
                totalInvoices,
                paidInvoices,
                partiallyPaidInvoices,
                unpaidInvoices,
                totalRevenue,
                totalPayments,
                outstandingBalance
        );
    }

    /**
     * Sales Report Summary Within a Date Range
     */
    public SalesReportResponse getSalesReport(
            LocalDate startDate,
            LocalDate endDate) {

        Long totalInvoices =
                invoiceRepository.countByInvoiceDateBetween(startDate, endDate);

        Long paidInvoices =
                invoiceRepository.countByStatusAndInvoiceDateBetween(
                        InvoiceStatus.PAID,
                        startDate,
                        endDate);

        Long partiallyPaidInvoices =
                invoiceRepository.countByStatusAndInvoiceDateBetween(
                        InvoiceStatus.PARTIALLY_PAID,
                        startDate,
                        endDate);

        Long unpaidInvoices =
                invoiceRepository.countByStatusAndInvoiceDateBetween(
                        InvoiceStatus.UNPAID,
                        startDate,
                        endDate);

        Double totalRevenue =
                invoiceRepository.calculateRevenueBetween(startDate, endDate);

        Double totalPayments =
                paymentRepository.calculatePaymentsBetween(startDate, endDate);

        Double outstandingBalance = totalRevenue - totalPayments;

        return new SalesReportResponse(
                totalInvoices,
                paidInvoices,
                partiallyPaidInvoices,
                unpaidInvoices,
                totalRevenue,
                totalPayments,
                outstandingBalance
        );
    }

    /**
     * Revenue Report
     */
    public RevenueReportResponse getRevenueReport(
            LocalDate startDate,
            LocalDate endDate) {

        Double totalRevenue =
                invoiceRepository.calculateRevenueBetween(startDate, endDate);

        Double totalPayments =
                paymentRepository.calculatePaymentsBetween(startDate, endDate);

        Double outstandingBalance = totalRevenue - totalPayments;

        return new RevenueReportResponse(
                startDate,
                endDate,
                totalRevenue,
                totalPayments,
                outstandingBalance
        );
    }

    /**
     * Monthly Revenue Report
     */
    public MonthlyRevenueResponse getMonthlyRevenueReport(
            Integer year,
            Integer month) {

        Double totalRevenue =
                invoiceRepository.calculateMonthlyRevenue(year, month);

        Double totalPayments =
                paymentRepository.calculateMonthlyPayments(year, month);

        Double outstandingBalance = totalRevenue - totalPayments;

        return new MonthlyRevenueResponse(
                year,
                month,
                totalRevenue,
                totalPayments,
                outstandingBalance
        );
    }

    /**
     * Payment Report
     */
    public PaymentReportResponse getPaymentReport() {

        Long totalPayments = paymentRepository.count();

        Long successfulPayments =
                paymentRepository.countByStatus(PaymentStatus.SUCCESS);

        Long pendingPayments =
                paymentRepository.countByStatus(PaymentStatus.PENDING);

        Long failedPayments =
                paymentRepository.countByStatus(PaymentStatus.FAILED);

        Long refundedPayments =
                paymentRepository.countByStatus(PaymentStatus.REFUNDED);

        Double totalAmount =
                paymentRepository.calculateTotalPayments();

        return new PaymentReportResponse(
                totalPayments,
                successfulPayments,
                pendingPayments,
                failedPayments,
                refundedPayments,
                totalAmount
        );
    }

    /**
     * Inventory Report
     */
    public InventoryReportResponse getInventoryReport() {

        Long totalProducts = productRepository.count();

        Long totalStockQuantity =
                productRepository.calculateTotalStockQuantity();

        Long lowStockProducts =
                productRepository.countLowStockProducts(10);

        Long outOfStockProducts =
                productRepository.countOutOfStockProducts();

        Double inventoryValue =
                productRepository.calculateInventoryValue();

        return new InventoryReportResponse(
                totalProducts,
                totalStockQuantity,
                lowStockProducts,
                outOfStockProducts,
                inventoryValue
        );
    }

    /**
     * Customer Report
     */
    public CustomerReportResponse getCustomerReport() {

        Long totalCustomers = customerRepository.count();

        Long customersWithInvoices =
                customerRepository.countCustomersWithInvoices(null);

        Long customersWithoutInvoices =
                customerRepository.countCustomersWithoutInvoices(null);

        Long customersWithOutstandingInvoices =
                customerRepository.countCustomersWithOutstandingInvoices(null);

        return new CustomerReportResponse(
                totalCustomers,
                customersWithInvoices,
                customersWithoutInvoices,
                customersWithOutstandingInvoices
        );
    }

        /**
        * Top Selling Products Report
        */
        public List<TopSellingProductResponse> getTopSellingProducts() {

        return invoiceItemRepository.getTopSellingProducts();
    }
}