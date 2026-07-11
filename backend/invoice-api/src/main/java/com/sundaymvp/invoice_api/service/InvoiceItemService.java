package com.sundaymvp.invoice_api.service;

import com.sundaymvp.invoice_api.dto.request.InvoiceItemRequest;
import com.sundaymvp.invoice_api.dto.response.InvoiceItemResponse;
import com.sundaymvp.invoice_api.entity.Invoice;
import com.sundaymvp.invoice_api.entity.InvoiceItem;
import com.sundaymvp.invoice_api.entity.Product;
import com.sundaymvp.invoice_api.exception.ResourceNotFoundException;
import com.sundaymvp.invoice_api.mapper.InvoiceItemMapper;
import com.sundaymvp.invoice_api.repository.InvoiceItemRepository;
import com.sundaymvp.invoice_api.repository.InvoiceRepository;
import com.sundaymvp.invoice_api.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class InvoiceItemService {

    private final InvoiceItemRepository invoiceItemRepository;
    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;

    public InvoiceItemService(
            InvoiceItemRepository invoiceItemRepository,
            InvoiceRepository invoiceRepository,
            ProductRepository productRepository) {

        this.invoiceItemRepository = invoiceItemRepository;
        this.invoiceRepository = invoiceRepository;
        this.productRepository = productRepository;
    }

    public List<InvoiceItemResponse> getAllInvoiceItems() {

        return invoiceItemRepository.findAll()
                .stream()
                .map(InvoiceItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    public InvoiceItemResponse getInvoiceItemById(Long id) {

        Objects.requireNonNull(id, "Invoice Item id must not be null");

        InvoiceItem invoiceItem = invoiceItemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invoice item not found"));

        return InvoiceItemMapper.toResponse(invoiceItem);
    }

    public InvoiceItemResponse saveInvoiceItem(InvoiceItemRequest request) {

        Objects.requireNonNull(request, "Invoice Item request must not be null");

        Invoice invoice = invoiceRepository.findById(request.getInvoiceId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invoice not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        InvoiceItem invoiceItem = new InvoiceItem();

        invoiceItem.setInvoice(invoice);
        invoiceItem.setProduct(product);
        invoiceItem.setQuantity(request.getQuantity());
        invoiceItem.setUnitPrice(request.getUnitPrice());
        invoiceItem.setDiscount(request.getDiscount());
        invoiceItem.setTax(request.getTax());

        double lineTotal =
                (request.getQuantity() * request.getUnitPrice())
                        - request.getDiscount()
                        + request.getTax();

        invoiceItem.setLineTotal(lineTotal);

        invoiceItemRepository.save(invoiceItem);

        return InvoiceItemMapper.toResponse(invoiceItem);
    }

    public InvoiceItemResponse updateInvoiceItem(Long id, InvoiceItemRequest request) {

        Objects.requireNonNull(id, "Invoice Item id must not be null");
        Objects.requireNonNull(request, "Invoice Item request must not be null");

        InvoiceItem invoiceItem = invoiceItemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invoice item not found"));

        Invoice invoice = invoiceRepository.findById(request.getInvoiceId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invoice not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        invoiceItem.setInvoice(invoice);
        invoiceItem.setProduct(product);
        invoiceItem.setQuantity(request.getQuantity());
        invoiceItem.setUnitPrice(request.getUnitPrice());
        invoiceItem.setDiscount(request.getDiscount());
        invoiceItem.setTax(request.getTax());

        double lineTotal =
                (request.getQuantity() * request.getUnitPrice())
                        - request.getDiscount()
                        + request.getTax();

        invoiceItem.setLineTotal(lineTotal);

        invoiceItemRepository.save(invoiceItem);

        return InvoiceItemMapper.toResponse(invoiceItem);
    }

    public void deleteInvoiceItem(Long id) {

        Objects.requireNonNull(id, "Invoice Item id must not be null");

        InvoiceItem invoiceItem = invoiceItemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invoice item not found"));

        invoiceItemRepository.delete(invoiceItem);
    }
}