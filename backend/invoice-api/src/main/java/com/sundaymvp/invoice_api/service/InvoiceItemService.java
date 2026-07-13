package com.sundaymvp.invoice_api.service;

import com.sundaymvp.invoice_api.dto.request.InvoiceItemRequest;
import com.sundaymvp.invoice_api.dto.response.InvoiceItemResponse;
import com.sundaymvp.invoice_api.entity.Invoice;
import com.sundaymvp.invoice_api.entity.InvoiceItem;
import com.sundaymvp.invoice_api.entity.Product;
import com.sundaymvp.invoice_api.exception.InsufficientStockException;
import com.sundaymvp.invoice_api.exception.ResourceNotFoundException;
import com.sundaymvp.invoice_api.mapper.InvoiceItemMapper;
import com.sundaymvp.invoice_api.repository.InvoiceItemRepository;
import com.sundaymvp.invoice_api.repository.InvoiceRepository;
import com.sundaymvp.invoice_api.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SuppressWarnings("null")
@Service
public class InvoiceItemService {

    private final InvoiceItemRepository invoiceItemRepository;
    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;
    private final InvoiceService invoiceService;

    public InvoiceItemService(
            InvoiceItemRepository invoiceItemRepository,
            InvoiceRepository invoiceRepository,
            ProductRepository productRepository,
            InvoiceService invoiceService) {

        this.invoiceItemRepository = invoiceItemRepository;
        this.invoiceRepository = invoiceRepository;
        this.productRepository = productRepository;
        this.invoiceService = invoiceService;
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

        validateStock(product, request.getQuantity());

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

        // Reduce stock
        product.setQuantity(product.getQuantity() - request.getQuantity());

        productRepository.save(product);

        invoiceItemRepository.save(invoiceItem);

        // Automatically update invoice total
        invoiceService.recalculateInvoiceTotal(invoice.getId());

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

        // Restore previous stock
        product.setQuantity(product.getQuantity() + invoiceItem.getQuantity());

        // Validate new stock
        validateStock(product, request.getQuantity());

        // Deduct new stock
        product.setQuantity(product.getQuantity() - request.getQuantity());

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

        productRepository.save(product);

        invoiceItemRepository.save(invoiceItem);

        // Automatically update invoice total
        invoiceService.recalculateInvoiceTotal(invoice.getId());

        return InvoiceItemMapper.toResponse(invoiceItem);
    }

    public void deleteInvoiceItem(Long id) {

        Objects.requireNonNull(id, "Invoice Item id must not be null");

        InvoiceItem invoiceItem = invoiceItemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invoice item not found"));

        Product product = invoiceItem.getProduct();

        // Restore stock
        product.setQuantity(product.getQuantity() + invoiceItem.getQuantity());

        productRepository.save(product);

        Long invoiceId = invoiceItem.getInvoice().getId();

        invoiceItemRepository.delete(invoiceItem);

        // Automatically update invoice total
        invoiceService.recalculateInvoiceTotal(invoiceId);
    }

    /**
     * Validate available stock.
     */
    private void validateStock(Product product, Integer quantity) {

        if (quantity > product.getQuantity()) {
            throw new InsufficientStockException("Insufficient stock available.");
        }
    }
}