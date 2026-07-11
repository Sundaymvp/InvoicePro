package com.sundaymvp.invoice_api.controller;

import com.sundaymvp.invoice_api.dto.request.InvoiceRequest;
import com.sundaymvp.invoice_api.dto.response.InvoiceResponse;
import com.sundaymvp.invoice_api.service.InvoiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public List<InvoiceResponse> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @GetMapping("/{id}")
    public InvoiceResponse getInvoice(@PathVariable Long id) {
        return invoiceService.getInvoiceById(id);
    }

    @PostMapping
    public InvoiceResponse createInvoice(@RequestBody InvoiceRequest request) {
        return invoiceService.saveInvoice(request);
    }

    @PutMapping("/{id}")
    public InvoiceResponse updateInvoice(
            @PathVariable Long id,
            @RequestBody InvoiceRequest request) {

        return invoiceService.updateInvoice(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
    }
}