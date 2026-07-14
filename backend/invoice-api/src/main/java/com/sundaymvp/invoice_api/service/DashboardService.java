package com.sundaymvp.invoice_api.service;

import com.sundaymvp.invoice_api.dto.response.DashboardResponse;
import com.sundaymvp.invoice_api.entity.Company;
import com.sundaymvp.invoice_api.enums.InvoiceStatus;
import com.sundaymvp.invoice_api.exception.ResourceNotFoundException;
import com.sundaymvp.invoice_api.repository.CompanyRepository;
import com.sundaymvp.invoice_api.repository.CustomerRepository;
import com.sundaymvp.invoice_api.repository.InvoiceRepository;
import com.sundaymvp.invoice_api.repository.PaymentRepository;
import com.sundaymvp.invoice_api.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;

    public DashboardService(
            CompanyRepository companyRepository,
            CustomerRepository customerRepository,
            ProductRepository productRepository,
            InvoiceRepository invoiceRepository,
            PaymentRepository paymentRepository) {

        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.invoiceRepository = invoiceRepository;
        this.paymentRepository = paymentRepository;
    }

    public DashboardResponse getDashboardSummary() {

        Company company = companyRepository.findById(1L)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found"));

        Long totalCustomers = customerRepository.countByCompany(company);

        Long totalProducts = productRepository.count();

        Long totalInvoices = invoiceRepository.countByCompany(company);

        Double totalRevenue = invoiceRepository.calculateTotalRevenue(company);

        if (totalRevenue == null) {
            totalRevenue = 0.0;
        }

        Double totalPayments = paymentRepository.calculateTotalPayments(company);

        if (totalPayments == null) {
            totalPayments = 0.0;
        }

        Double outstandingBalance = totalRevenue - totalPayments;

        Long lowStockProducts =
                productRepository.countLowStockProducts(company, 10);

        Long paidInvoices =
                invoiceRepository.countByCompanyAndStatus(
                        company,
                        InvoiceStatus.PAID);

        Long partiallyPaidInvoices =
                invoiceRepository.countByCompanyAndStatus(
                        company,
                        InvoiceStatus.PARTIALLY_PAID);

        Long unpaidInvoices =
                invoiceRepository.countByCompanyAndStatus(
                        company,
                        InvoiceStatus.UNPAID);

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