package com.sundaymvp.invoice_api.service;

import com.sundaymvp.invoice_api.audit.Audit;
import com.sundaymvp.invoice_api.dto.request.InvoiceRequest;
import com.sundaymvp.invoice_api.dto.response.InvoiceResponse;
import com.sundaymvp.invoice_api.entity.Company;
import com.sundaymvp.invoice_api.entity.Customer;
import com.sundaymvp.invoice_api.entity.Invoice;
import com.sundaymvp.invoice_api.exception.ResourceNotFoundException;
import com.sundaymvp.invoice_api.mapper.InvoiceMapper;
import com.sundaymvp.invoice_api.repository.CompanyRepository;
import com.sundaymvp.invoice_api.repository.CustomerRepository;
import com.sundaymvp.invoice_api.repository.InvoiceItemRepository;
import com.sundaymvp.invoice_api.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SuppressWarnings("null")
@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;
    private final InvoiceItemRepository invoiceItemRepository;
    private final InvoiceEmailService invoiceEmailService;

    public InvoiceService(
            InvoiceRepository invoiceRepository,
            CustomerRepository customerRepository,
            CompanyRepository companyRepository,
            InvoiceItemRepository invoiceItemRepository,
            InvoiceEmailService invoiceEmailService) {

        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
        this.companyRepository = companyRepository;
        this.invoiceItemRepository = invoiceItemRepository;
        this.invoiceEmailService = invoiceEmailService;
    }

    public List<InvoiceResponse> getAllInvoices() {

        return invoiceRepository.findAll()
                .stream()
                .map(InvoiceMapper::toResponse)
                .collect(Collectors.toList());
    }

    public InvoiceResponse getInvoiceById(Long id) {

        Objects.requireNonNull(id, "Invoice id must not be null");

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invoice not found"));

        return InvoiceMapper.toResponse(invoice);
    }

    @Audit(
        module = "Invoice",
        action = "CREATE"
)
    public InvoiceResponse saveInvoice(InvoiceRequest request) {

        Objects.requireNonNull(request, "Invoice request must not be null");

        if (invoiceRepository.existsByInvoiceNumber(request.getInvoiceNumber())) {
            throw new RuntimeException("Invoice number already exists");
        }

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found"));

        Company company = companyRepository.findById(1L)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found"));

        Invoice invoice = new Invoice();

        invoice.setCompany(company);
        invoice.setInvoiceNumber(request.getInvoiceNumber());
        invoice.setCustomer(customer);
        invoice.setInvoiceDate(request.getInvoiceDate());
        invoice.setDueDate(request.getDueDate());

        // Always starts at zero.
        invoice.setTotalAmount(0.0);

        invoice.setStatus(request.getStatus());
        invoice.setNotes(request.getNotes());

        return InvoiceMapper.toResponse(invoiceRepository.save(invoice));
    }

    @Audit(
        module = "Invoice",
        action = "UPDATE"
)
    public InvoiceResponse updateInvoice(Long id, InvoiceRequest request) {

        Objects.requireNonNull(id, "Invoice id must not be null");
        Objects.requireNonNull(request, "Invoice request must not be null");

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invoice not found"));

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found"));

        Company company = companyRepository.findById(1L)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found"));

        invoice.setCompany(company);
        invoice.setInvoiceNumber(request.getInvoiceNumber());
        invoice.setCustomer(customer);
        invoice.setInvoiceDate(request.getInvoiceDate());
        invoice.setDueDate(request.getDueDate());

        // Total amount is calculated automatically.
        invoice.setStatus(request.getStatus());
        invoice.setNotes(request.getNotes());

        return InvoiceMapper.toResponse(invoiceRepository.save(invoice));
    }

    @Audit(
        module = "Invoice",
        action = "DELETE"
)
    public void deleteInvoice(Long id) {

        Objects.requireNonNull(id, "Invoice id must not be null");

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invoice not found"));

        invoiceRepository.delete(invoice);
    }

    /**
     * Recalculate invoice total automatically.
     */
    public void recalculateInvoiceTotal(Long invoiceId) {

        Objects.requireNonNull(invoiceId, "Invoice id must not be null");

        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invoice not found"));

        double total = Objects.requireNonNullElse(
                invoiceItemRepository.calculateInvoiceTotal(invoiceId),
                0.0
        );

        invoice.setTotalAmount(total);

        Invoice updatedInvoice = invoiceRepository.save(invoice);

        invoiceEmailService.sendInvoice(
                updatedInvoice.getCustomer().getEmail(),
                updatedInvoice.getCustomer().getName(),
                updatedInvoice.getInvoiceNumber(),
                updatedInvoice.getTotalAmount()
        );
    }
}