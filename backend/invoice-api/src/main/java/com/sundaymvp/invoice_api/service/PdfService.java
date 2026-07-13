package com.sundaymvp.invoice_api.service;

import com.sundaymvp.invoice_api.entity.Invoice;
import com.sundaymvp.invoice_api.entity.InvoiceItem;
import com.sundaymvp.invoice_api.exception.ResourceNotFoundException;
import com.sundaymvp.invoice_api.repository.InvoiceRepository;
import com.sundaymvp.invoice_api.repository.InvoiceItemRepository;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Objects;

@Service
public class PdfService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemRepository invoiceItemRepository;

    public PdfService(
        InvoiceRepository invoiceRepository,
        InvoiceItemRepository invoiceItemRepository) {

    this.invoiceRepository = invoiceRepository;
    this.invoiceItemRepository = invoiceItemRepository;
}

    /**
     * Generate Invoice PDF
     */
    public byte[] generateInvoicePdf(Long invoiceId) {

        Objects.requireNonNull(invoiceId, "Invoice id must not be null");   

        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invoice not found"));

        try {

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            Document document = new Document();

            PdfWriter.getInstance(document, outputStream);

            document.open();

            document.add(new Paragraph("MVP INVOICE SYSTEM"));

            document.add(new Paragraph(" "));

            document.add(new Paragraph(
                    "Invoice Number: " +
                            invoice.getInvoiceNumber()));

            document.add(new Paragraph(
                    "Invoice Date: " +
                            invoice.getInvoiceDate()));

            document.add(new Paragraph(
                    "Status: " +
                            invoice.getStatus()));

            document.add(new Paragraph(" "));

            document.add(new Paragraph(
                    "Customer: " +
                            invoice.getCustomer().getName()));

            document.add(new Paragraph(
                    "Phone: " +
                            invoice.getCustomer().getPhone()));

            document.add(new Paragraph(
                    "Email: " +
                            invoice.getCustomer().getEmail()));

            document.add(new Paragraph(" "));

            List<InvoiceItem> items =
        invoiceItemRepository.findByInvoiceId(invoiceId);

            for (InvoiceItem item : items) {

                document.add(new Paragraph(

                        item.getProduct().getName()
                                + " | Qty: "
                                + item.getQuantity()
                                + " | Price: "
                                + item.getUnitPrice()
                                + " | Total: "
                                + item.getLineTotal()

                ));
            }

            document.add(new Paragraph(" "));

            document.add(new Paragraph(
                    "Grand Total: ₦" +
                            invoice.getTotalAmount()));

            document.close();

            return outputStream.toByteArray();

        } catch (DocumentException ex) {

            throw new RuntimeException(
                    "Unable to generate PDF", ex);

        }

    }

}