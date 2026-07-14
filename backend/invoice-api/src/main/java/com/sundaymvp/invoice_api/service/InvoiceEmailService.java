package com.sundaymvp.invoice_api.service;

import com.sundaymvp.invoice_api.entity.Company;
import com.sundaymvp.invoice_api.exception.ResourceNotFoundException;
import com.sundaymvp.invoice_api.repository.CompanyRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class InvoiceEmailService {

    private final JavaMailSender mailSender;
    private final CompanyRepository companyRepository;

    public InvoiceEmailService(
            JavaMailSender mailSender,
            CompanyRepository companyRepository) {

        this.mailSender = mailSender;
        this.companyRepository = companyRepository;
    }

    /**
     * Send invoice notification email.
     */
    public void sendInvoice(
            String email,
            String customerName,
            String invoiceNumber,
            Double amount) {

        Company company = companyRepository.findById(1L)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found"));

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);

        message.setSubject(company.getCompanyName() + " - Invoice " + invoiceNumber);

        message.setText(
                "Hello " + customerName + ",\n\n"
                        + "Thank you for choosing " + company.getCompanyName() + ".\n\n"
                        + "Your invoice has been generated successfully.\n\n"
                        + "Invoice Number : " + invoiceNumber + "\n"
                        + "Total Amount   : "
                        + company.getCurrencySymbol() + amount + "\n\n"
                        + "If you have any questions, kindly contact us.\n\n"
                        + "Phone   : " + company.getPhone() + "\n"
                        + "Email   : " + company.getEmail() + "\n"
                        + "Website : " + company.getWebsite() + "\n\n"
                        + "Thank you for your business.\n\n"
                        + company.getCompanyName()
        );

        // Console Log
        System.out.println("========== EMAIL SENT ==========");
        System.out.println("Company : " + company.getCompanyName());
        System.out.println("To      : " + email);
        System.out.println("Customer: " + customerName);
        System.out.println("Invoice : " + invoiceNumber);
        System.out.println("Amount  : " + company.getCurrencySymbol() + amount);
        System.out.println("================================");

        mailSender.send(message);
    }
}