package com.sundaymvp.invoice_api.controller;

import com.sundaymvp.invoice_api.dto.request.PaymentRequest;
import com.sundaymvp.invoice_api.dto.response.PaymentResponse;
import com.sundaymvp.invoice_api.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public List<PaymentResponse> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    public PaymentResponse getPayment(@PathVariable Long id) {
        return paymentService.getPaymentById(id);
    }

    @PostMapping
    public PaymentResponse createPayment(@RequestBody PaymentRequest request) {
        return paymentService.savePayment(request);
    }

    @PutMapping("/{id}")
    public PaymentResponse updatePayment(
            @PathVariable Long id,
            @RequestBody PaymentRequest request) {

        return paymentService.updatePayment(id, request);
    }

    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
    }
}