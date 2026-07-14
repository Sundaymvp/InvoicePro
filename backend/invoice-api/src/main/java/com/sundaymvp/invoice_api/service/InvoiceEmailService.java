package com.sundaymvp.invoice_api.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class InvoiceEmailService {

    private final JavaMailSender mailSender;

    public InvoiceEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Send invoice notification email to customer.
     */
    public void sendInvoice(
            String email,
            String customerName,
            String invoiceNumber,
            Double amount) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);

        message.setSubject("Invoice " + invoiceNumber);

        message.setText(
                "Hello " + customerName + ",\n\n"
                        + "Thank you for choosing Sunday MVP Technologies.\n\n"
                        + "Your invoice has been generated successfully.\n\n"
                        + "Invoice Number: " + invoiceNumber + "\n"
                        + "Total Amount: ₦" + amount + "\n\n"
                        + "Please log in to your account to make payment.\n\n"
                        + "Thank you.\n\n"
                        + "Sunday MVP Technologies"
        );

        System.out.println("========== EMAIL TEST ==========");
System.out.println("To: " + email);
System.out.println("Customer: " + customerName);
System.out.println("Invoice: " + invoiceNumber);
System.out.println("Amount: " + amount);
System.out.println("================================");

        mailSender.send(message);
    }
}