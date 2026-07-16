package com.sundaymvp.invoice_api.controller;

import com.sundaymvp.invoice_api.dto.request.InvoiceItemRequest;
import com.sundaymvp.invoice_api.dto.response.InvoiceItemResponse;
import com.sundaymvp.invoice_api.service.InvoiceItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoice-items")
@CrossOrigin(origins = "*")
public class InvoiceItemController {

    private final InvoiceItemService invoiceItemService;

    public InvoiceItemController(InvoiceItemService invoiceItemService) {
        this.invoiceItemService = invoiceItemService;
    }

    /**
     * Get all invoice items
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','ACCOUNTANT','SALES')")
    public ResponseEntity<List<InvoiceItemResponse>> getAllInvoiceItems() {

        return ResponseEntity.ok(
                invoiceItemService.getAllInvoiceItems());
    }

    /**
     * Get invoice item by ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','ACCOUNTANT','SALES')")
    public ResponseEntity<InvoiceItemResponse> getInvoiceItem(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                invoiceItemService.getInvoiceItemById(id));
    }

    /**
     * Create invoice item
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','SALES')")
    public ResponseEntity<InvoiceItemResponse> createInvoiceItem(
            @Valid @RequestBody InvoiceItemRequest request) {

        return new ResponseEntity<>(
                invoiceItemService.saveInvoiceItem(request),
                HttpStatus.CREATED);
    }

    /**
     * Update invoice item
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<InvoiceItemResponse> updateInvoiceItem(
            @PathVariable Long id,
            @Valid @RequestBody InvoiceItemRequest request) {

        return ResponseEntity.ok(
                invoiceItemService.updateInvoiceItem(id, request));
    }

    /**
     * Delete invoice item
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteInvoiceItem(
            @PathVariable Long id) {

        invoiceItemService.deleteInvoiceItem(id);

        return ResponseEntity.ok(
                "Invoice item deleted successfully.");
    }
}