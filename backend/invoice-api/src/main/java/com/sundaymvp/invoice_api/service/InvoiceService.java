package com.sundaymvp.invoice_api.service;

import com.sundaymvp.invoice_api.dto.request.InvoiceRequest;
import com.sundaymvp.invoice_api.dto.response.InvoiceResponse;
import com.sundaymvp.invoice_api.entity.Customer;
import com.sundaymvp.invoice_api.entity.Invoice;
import com.sundaymvp.invoice_api.exception.ResourceNotFoundException;
import com.sundaymvp.invoice_api.mapper.InvoiceMapper;
import com.sundaymvp.invoice_api.repository.CustomerRepository;
import com.sundaymvp.invoice_api.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;

    public InvoiceService(InvoiceRepository invoiceRepository,
                          CustomerRepository customerRepository) {
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
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
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));

        return InvoiceMapper.toResponse(invoice);
    }

    public InvoiceResponse saveInvoice(InvoiceRequest request) {

        Objects.requireNonNull(request, "Invoice request must not be null");

        if (invoiceRepository.existsByInvoiceNumber(request.getInvoiceNumber())) {
            throw new RuntimeException("Invoice number already exists");
        }

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Invoice invoice = new Invoice();

        invoice.setInvoiceNumber(request.getInvoiceNumber());
        invoice.setCustomer(customer);
        invoice.setInvoiceDate(request.getInvoiceDate());
        invoice.setDueDate(request.getDueDate());
        invoice.setTotalAmount(request.getTotalAmount());
        invoice.setStatus(request.getStatus());
        invoice.setNotes(request.getNotes());

        Invoice savedInvoice = invoiceRepository.save(invoice);

        return InvoiceMapper.toResponse(savedInvoice);
    }

    public InvoiceResponse updateInvoice(Long id, InvoiceRequest request) {

        Objects.requireNonNull(id, "Invoice id must not be null");
        Objects.requireNonNull(request, "Invoice request must not be null");

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        invoice.setInvoiceNumber(request.getInvoiceNumber());
        invoice.setCustomer(customer);
        invoice.setInvoiceDate(request.getInvoiceDate());
        invoice.setDueDate(request.getDueDate());
        invoice.setTotalAmount(request.getTotalAmount());
        invoice.setStatus(request.getStatus());
        invoice.setNotes(request.getNotes());

        Invoice updatedInvoice = invoiceRepository.save(invoice);

        return InvoiceMapper.toResponse(updatedInvoice);
    }

    public void deleteInvoice(Long id) {

        Objects.requireNonNull(id, "Invoice id must not be null");

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));

        invoiceRepository.delete(invoice);
    }
}