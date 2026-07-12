package com.sundaymvp.invoice_api.service;

import com.sundaymvp.invoice_api.dto.response.DashboardResponse;
import com.sundaymvp.invoice_api.enums.InvoiceStatus;
import com.sundaymvp.invoice_api.repository.CustomerRepository;
import com.sundaymvp.invoice_api.repository.InvoiceRepository;
import com.sundaymvp.invoice_api.repository.PaymentRepository;
import com.sundaymvp.invoice_api.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;

    public DashboardService(
            CustomerRepository customerRepository,
            ProductRepository productRepository,
            InvoiceRepository invoiceRepository,
            PaymentRepository paymentRepository) {

        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.invoiceRepository = invoiceRepository;
        this.paymentRepository = paymentRepository;
    }

    public DashboardResponse getDashboardSummary() {

        Long totalCustomers = customerRepository.count();

        Long totalProducts = productRepository.count();

        Long totalInvoices = invoiceRepository.count();

        Double totalRevenue = invoiceRepository.calculateTotalRevenue();

        Double totalPayments = paymentRepository.calculateTotalPayments();

        Double outstandingBalance = totalRevenue - totalPayments;

        Long lowStockProducts = productRepository.countLowStockProducts(10);

        Long paidInvoices = invoiceRepository.countByStatus(InvoiceStatus.PAID);

        Long partiallyPaidInvoices =
                invoiceRepository.countByStatus(InvoiceStatus.PARTIALLY_PAID);

        Long unpaidInvoices =
                invoiceRepository.countByStatus(InvoiceStatus.UNPAID);

        return new DashboardResponse(
                totalCustomers,
                totalProducts,
                totalInvoices,
                totalRevenue,
                totalPayments,
                outstandingBalance,
                lowStockProducts,
                paidInvoices,
                partiallyPaidInvoices,
                unpaidInvoices
        );
    }
}