package com.sundaymvp.invoice_api.service;

import com.sundaymvp.invoice_api.audit.Audit;
import com.sundaymvp.invoice_api.dto.request.PaymentRequest;
import com.sundaymvp.invoice_api.dto.response.PaymentResponse;
import com.sundaymvp.invoice_api.entity.Invoice;
import com.sundaymvp.invoice_api.entity.Payment;
import com.sundaymvp.invoice_api.enums.InvoiceStatus;
import com.sundaymvp.invoice_api.exception.ResourceNotFoundException;
import com.sundaymvp.invoice_api.mapper.PaymentMapper;
import com.sundaymvp.invoice_api.repository.InvoiceRepository;
import com.sundaymvp.invoice_api.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SuppressWarnings("null")
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;

    public PaymentService(
            PaymentRepository paymentRepository,
            InvoiceRepository invoiceRepository) {

        this.paymentRepository = paymentRepository;
        this.invoiceRepository = invoiceRepository;
    }

    /**
     * Get all payments
     */
    public List<PaymentResponse> getAllPayments() {

        return paymentRepository.findAll()
                .stream()
                .map(PaymentMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get payment by id
     */
    public PaymentResponse getPaymentById(Long id) {

        Objects.requireNonNull(id, "Payment id must not be null");

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found"));

        return PaymentMapper.toResponse(payment);
    }

    /**
     * Save payment
     */
    @Audit(
        module = "Payment",
        action = "CREATE"
)
    public PaymentResponse savePayment(PaymentRequest request) {

        Objects.requireNonNull(request, "Payment request must not be null");

        Invoice invoice = invoiceRepository.findById(request.getInvoiceId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invoice not found"));

        // Prevent duplicate payment reference
        if (request.getReferenceNumber() != null
                && !request.getReferenceNumber().isBlank()
                && paymentRepository.existsByReferenceNumber(request.getReferenceNumber())) {

            throw new RuntimeException("Payment reference already exists.");
        }

        // Calculate amount already paid
        Double totalPaid = paymentRepository.calculateTotalPaid(invoice.getId());

        if (totalPaid == null) {
            totalPaid = 0.0;
        }

        // Prevent overpayment
        if (totalPaid + request.getAmount() > invoice.getTotalAmount()) {
            throw new RuntimeException("Payment exceeds invoice balance.");
        }

        Payment payment = new Payment();

        payment.setInvoice(invoice);
        payment.setPaymentDate(request.getPaymentDate());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setAmount(request.getAmount());
        payment.setReferenceNumber(request.getReferenceNumber());
        payment.setNotes(request.getNotes());
        payment.setStatus(request.getStatus());

        Payment savedPayment = paymentRepository.save(payment);

        // Refresh total paid
        updateInvoiceStatus(invoice);
        
        return PaymentMapper.toResponse(savedPayment);
    }

    /**
 * Update payment
 */
@Audit(
        module = "Payment",
        action = "UPDATE"
)
public PaymentResponse updatePayment(Long id, PaymentRequest request) {

    Objects.requireNonNull(id, "Payment id must not be null");
    Objects.requireNonNull(request, "Payment request must not be null");

    Payment payment = paymentRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Payment not found"));

    Invoice invoice = invoiceRepository.findById(request.getInvoiceId())
            .orElseThrow(() ->
                    new ResourceNotFoundException("Invoice not found"));

    // Check duplicate reference number
    if (request.getReferenceNumber() != null
            && !request.getReferenceNumber().isBlank()) {

        paymentRepository.findByReferenceNumber(request.getReferenceNumber())
                .ifPresent(existingPayment -> {

                    if (!existingPayment.getId().equals(id)) {
                        throw new RuntimeException("Payment reference already exists.");
                    }
                });
    }

    // Calculate total paid excluding this payment
    Double totalPaid =
            paymentRepository.calculateTotalPaid(invoice.getId()) - payment.getAmount();

    if (totalPaid < 0) {
        totalPaid = 0.0;
    }

    // Prevent overpayment
    if (totalPaid + request.getAmount() > invoice.getTotalAmount()) {
        throw new RuntimeException("Payment exceeds invoice balance.");
    }

    payment.setInvoice(invoice);
    payment.setPaymentDate(request.getPaymentDate());
    payment.setPaymentMethod(request.getPaymentMethod());
    payment.setAmount(request.getAmount());
    payment.setReferenceNumber(request.getReferenceNumber());
    payment.setNotes(request.getNotes());
    payment.setStatus(request.getStatus());

    Payment updatedPayment = paymentRepository.save(payment);

    updateInvoiceStatus(invoice);

    return PaymentMapper.toResponse(updatedPayment);
}

/**
 * Delete payment
 */
@Audit(
        module = "Payment",
        action = "DELETE"
)
public void deletePayment(Long id) {

    Objects.requireNonNull(id, "Payment id must not be null");

    Payment payment = paymentRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Payment not found"));

    Invoice invoice = payment.getInvoice();

    paymentRepository.delete(payment);

    updateInvoiceStatus(invoice); Double totalPaid = paymentRepository.calculateTotalPaid(invoice.getId());

    if (totalPaid == null) {
        totalPaid = 0.0;
    }

    if (totalPaid <= 0) {
        invoice.setStatus(InvoiceStatus.UNPAID);
    } else if (totalPaid < invoice.getTotalAmount()) {
        invoice.setStatus(InvoiceStatus.PARTIALLY_PAID);
    } else {
        invoice.setStatus(InvoiceStatus.PAID);
    }

    invoiceRepository.save(invoice);
}

/**
 * Updates the invoice payment status based on total amount paid.
 */
private void updateInvoiceStatus(Invoice invoice) {

    Double totalPaid = paymentRepository.calculateTotalPaid(invoice.getId());

    if (totalPaid == null) {
        totalPaid = 0.0;
    }

    if (totalPaid <= 0) {
        invoice.setStatus(InvoiceStatus.UNPAID);
    } else if (totalPaid < invoice.getTotalAmount()) {
        invoice.setStatus(InvoiceStatus.PARTIALLY_PAID);
    } else {
        invoice.setStatus(InvoiceStatus.PAID);
    }

    invoiceRepository.save(invoice);
}

}