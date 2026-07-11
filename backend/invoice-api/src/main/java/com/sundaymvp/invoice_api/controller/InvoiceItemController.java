package com.sundaymvp.invoice_api.controller;

import com.sundaymvp.invoice_api.dto.request.InvoiceItemRequest;
import com.sundaymvp.invoice_api.dto.response.InvoiceItemResponse;
import com.sundaymvp.invoice_api.service.InvoiceItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoice-items")
public class InvoiceItemController {

    private final InvoiceItemService invoiceItemService;

    public InvoiceItemController(InvoiceItemService invoiceItemService) {
        this.invoiceItemService = invoiceItemService;
    }


    // GET ALL INVOICE ITEMS
    @GetMapping
    public ResponseEntity<List<InvoiceItemResponse>> getAllInvoiceItems() {

        return ResponseEntity.ok(
                invoiceItemService.getAllInvoiceItems()
        );
    }


    // GET INVOICE ITEM BY ID
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceItemResponse> getInvoiceItemById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                invoiceItemService.getInvoiceItemById(id)
        );
    }


    // CREATE INVOICE ITEM
    @PostMapping
    public ResponseEntity<InvoiceItemResponse> createInvoiceItem(
            @RequestBody InvoiceItemRequest request) {

        return new ResponseEntity<>(
                invoiceItemService.saveInvoiceItem(request),
                HttpStatus.CREATED
        );
    }


    // UPDATE INVOICE ITEM
    @PutMapping("/{id}")
    public ResponseEntity<InvoiceItemResponse> updateInvoiceItem(
            @PathVariable Long id,
            @RequestBody InvoiceItemRequest request) {

        return ResponseEntity.ok(
                invoiceItemService.updateInvoiceItem(id, request)
        );
    }


    // DELETE INVOICE ITEM
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoiceItem(
            @PathVariable Long id) {

        invoiceItemService.deleteInvoiceItem(id);

        return ResponseEntity.noContent().build();
    }
}