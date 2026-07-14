package com.sundaymvp.invoice_api.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import com.sundaymvp.invoice_api.entity.Invoice;
import com.sundaymvp.invoice_api.entity.InvoiceItem;
import com.sundaymvp.invoice_api.exception.ResourceNotFoundException;
import com.sundaymvp.invoice_api.repository.InvoiceItemRepository;
import com.sundaymvp.invoice_api.repository.InvoiceRepository;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.awt.Color;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
public class PdfService {

    private static final Font TITLE_FONT =
            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);

    private static final Font HEADER_FONT =
            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

    private static final Font NORMAL_FONT =
            FontFactory.getFont(FontFactory.HELVETICA, 10);

    private static final Font TABLE_HEADER_FONT =
            FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    10,
                    Color.WHITE);

    private static final NumberFormat CURRENCY =
            NumberFormat.getCurrencyInstance(Locale.of("en", "NG"));

    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemRepository invoiceItemRepository;

    public PdfService(
            InvoiceRepository invoiceRepository,
            InvoiceItemRepository invoiceItemRepository) {

        this.invoiceRepository = invoiceRepository;
        this.invoiceItemRepository = invoiceItemRepository;
    }

    public byte[] generateInvoicePdf(Long invoiceId) {

        Objects.requireNonNull(invoiceId, "Invoice id must not be null");

        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invoice not found"));

        try {

        

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            Document document = new Document(PageSize.A4);

            PdfWriter.getInstance(document, outputStream);

            document.open();

            //-----------------------------------------
            // COMPANY HEADER
            //-----------------------------------------

            Paragraph title =
                    new Paragraph("MVP INVOICE SYSTEM", TITLE_FONT);

            title.setAlignment(Element.ALIGN_CENTER);

            document.add(title);

            Paragraph company =
                    new Paragraph(
                            """
                            Sunday MVP Technologies
                            Lagos, Nigeria

                            Email: support@mvpinvoice.com
                            Phone: +234 800 000 0000
                            Website: www.mvpinvoice.com
                            """,
                            NORMAL_FONT);

            company.setAlignment(Element.ALIGN_CENTER);

            document.add(company);

            document.add(new Paragraph(" "));

            //-----------------------------------------
            // INVOICE DETAILS
            //-----------------------------------------

            PdfPTable infoTable = new PdfPTable(2);

            infoTable.setWidthPercentage(100);

            infoTable.setSpacingAfter(15);

            infoTable.setWidths(new float[]{1,2});

            infoTable.addCell(createLabelCell("Invoice Number"));
            infoTable.addCell(createValueCell(invoice.getInvoiceNumber()));

            infoTable.addCell(createLabelCell("Invoice Date"));
            infoTable.addCell(createValueCell(invoice.getInvoiceDate().toString()));

            infoTable.addCell(createLabelCell("Due Date"));
            infoTable.addCell(createValueCell(invoice.getDueDate().toString()));

            infoTable.addCell(createLabelCell("Status"));
            infoTable.addCell(createValueCell(invoice.getStatus().name()));

            document.add(infoTable);

            //-----------------------------------------
            // CUSTOMER
            //-----------------------------------------

            document.add(new Paragraph("Customer Details", HEADER_FONT));

            document.add(new Paragraph(
                    "Name : " + invoice.getCustomer().getName(),
                    NORMAL_FONT));

            document.add(new Paragraph(
                    "Phone : " + invoice.getCustomer().getPhone(),
                    NORMAL_FONT));

            document.add(new Paragraph(
                    "Email : " + invoice.getCustomer().getEmail(),
                    NORMAL_FONT));

            document.add(new Paragraph(" "));

            //-----------------------------------------
            // PRODUCTS TABLE
            //-----------------------------------------

            PdfPTable table = new PdfPTable(4);

            table.setWidthPercentage(100);

            table.setWidths(new float[]{4,1,2,2});

            addHeader(table, "Product");
            addHeader(table, "Qty");
            addHeader(table, "Unit Price");
            addHeader(table, "Total");

            List<InvoiceItem> items =
                    invoiceItemRepository.findByInvoiceId(invoiceId);

            for (InvoiceItem item : items) {

                table.addCell(item.getProduct().getName());

                table.addCell(String.valueOf(item.getQuantity()));

                table.addCell(CURRENCY.format(item.getUnitPrice()));

                table.addCell(CURRENCY.format(item.getLineTotal()));
            }

            document.add(table);

            document.add(new Paragraph(" "));

            //-----------------------------------------
            // TOTAL
            //-----------------------------------------

            Paragraph total =
                    new Paragraph(
                            "Grand Total : "
                                    + CURRENCY.format(invoice.getTotalAmount()),
                            HEADER_FONT);

            total.setAlignment(Element.ALIGN_RIGHT);

            document.add(total);

            document.add(new Paragraph(" "));

            Paragraph footer =
                    new Paragraph(
                            "Thank you for doing business with us.",
                            NORMAL_FONT);

            footer.setAlignment(Element.ALIGN_CENTER);

            document.add(footer);

            document.close();

            return outputStream.toByteArray();

        } catch (Exception ex) {

            throw new RuntimeException("Unable to generate PDF", ex);
        }
    }
        public byte[] getInvoicePdf(Long invoiceId) {
    return generateInvoicePdf(invoiceId);
}

    //-------------------------------------------------

    private PdfPCell createLabelCell(String text) {

        PdfPCell cell = new PdfPCell(new Phrase(text, HEADER_FONT));

        cell.setBorder(Rectangle.NO_BORDER);

        return cell;
    }

    private PdfPCell createValueCell(String text) {

        PdfPCell cell = new PdfPCell(new Phrase(text, NORMAL_FONT));

        cell.setBorder(Rectangle.NO_BORDER);

        return cell;
    }

    private void addHeader(PdfPTable table, String title) {

        PdfPCell cell =
                new PdfPCell(new Phrase(title, TABLE_HEADER_FONT));

        cell.setBackgroundColor(Color.DARK_GRAY);

        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(cell);
    }
}