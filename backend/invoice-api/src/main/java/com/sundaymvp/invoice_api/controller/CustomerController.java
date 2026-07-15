package com.sundaymvp.invoice_api.controller;

import com.sundaymvp.invoice_api.dto.response.CustomerResponse;
import com.sundaymvp.invoice_api.entity.Customer;
import com.sundaymvp.invoice_api.mapper.CustomerMapper;
import com.sundaymvp.invoice_api.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
public List<CustomerResponse> getAllCustomers() {
    return customerService.getAllCustomers();

}

    @GetMapping("/{id}")
    public CustomerResponse getCustomer(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
public CustomerResponse createCustomer(@RequestBody Customer customer) {

    Customer saved = customerService.saveCustomer(customer);

    return CustomerMapper.toResponse(saved);

    }

    @PutMapping("/{id}")
public CustomerResponse updateCustomer(
        @PathVariable Long id,
        @RequestBody Customer customer) {

    Customer updated = customerService.updateCustomer(id, customer);

    return CustomerMapper.toResponse(updated);

    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}