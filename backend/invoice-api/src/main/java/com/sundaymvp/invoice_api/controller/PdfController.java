package com.sundaymvp.invoice_api.controller;

import com.sundaymvp.invoice_api.service.PdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
public class PdfController {

    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    /**
     * Download Invoice PDF
     *
     * Example:
     * GET /api/invoices/1/pdf
     */
    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> downloadInvoice(
            @PathVariable Long id) {

        byte[] pdf = pdfService.generateInvoicePdf(id);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=Invoice_" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}