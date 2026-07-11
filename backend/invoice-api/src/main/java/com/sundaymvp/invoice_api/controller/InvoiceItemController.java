package com.sundaymvp.invoice_api.controller;

import com.sundaymvp.invoice_api.dto.request.InvoiceItemRequest;
import com.sundaymvp.invoice_api.dto.response.InvoiceItemResponse;
import com.sundaymvp.invoice_api.service.InvoiceItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoice-items")
public class InvoiceItemController {

    private final InvoiceItemService invoiceItemService;

    public InvoiceItemController(InvoiceItemService invoiceItemService) {
        this.invoiceItemService = invoiceItemService;
    }

    @GetMapping
    public List<InvoiceItemResponse> getAllInvoiceItems() {
        return invoiceItemService.getAllInvoiceItems();
    }

    @GetMapping("/{id}")
    public InvoiceItemResponse getInvoiceItem(@PathVariable Long id) {
        return invoiceItemService.getInvoiceItemById(id);
    }

    @PostMapping
    public InvoiceItemResponse createInvoiceItem(
            @RequestBody InvoiceItemRequest request) {

        return invoiceItemService.saveInvoiceItem(request);
    }

    @PutMapping("/{id}")
    public InvoiceItemResponse updateInvoiceItem(
            @PathVariable Long id,
            @RequestBody InvoiceItemRequest request) {

        return invoiceItemService.updateInvoiceItem(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteInvoiceItem(@PathVariable Long id) {
        invoiceItemService.deleteInvoiceItem(id);
    }
}