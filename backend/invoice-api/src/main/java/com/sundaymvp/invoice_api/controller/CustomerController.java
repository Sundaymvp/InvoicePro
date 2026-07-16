package com.sundaymvp.invoice_api.controller;

import com.sundaymvp.invoice_api.dto.response.CustomerResponse;
import com.sundaymvp.invoice_api.entity.Customer;
import com.sundaymvp.invoice_api.mapper.CustomerMapper;
import com.sundaymvp.invoice_api.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Get all customers
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','ACCOUNTANT','SALES')")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {

        return ResponseEntity.ok(
                customerService.getAllCustomers());
    }

    /**
     * Get customer by ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','ACCOUNTANT','SALES')")
    public ResponseEntity<CustomerResponse> getCustomer(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                customerService.getCustomerById(id));
    }

    /**
     * Create customer
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','SALES')")
    public ResponseEntity<CustomerResponse> createCustomer(
            @RequestBody Customer customer) {

        Customer saved = customerService.saveCustomer(customer);

        return new ResponseEntity<>(
                CustomerMapper.toResponse(saved),
                HttpStatus.CREATED);
    }

    /**
     * Update customer
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable Long id,
            @RequestBody Customer customer) {

        Customer updated = customerService.updateCustomer(id, customer);

        return ResponseEntity.ok(
                CustomerMapper.toResponse(updated));
    }

    /**
     * Delete customer
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCustomer(
            @PathVariable Long id) {

        customerService.deleteCustomer(id);

        return ResponseEntity.ok(
                "Customer deleted successfully.");
    }
}