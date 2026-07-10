package com.sundaymvp.invoice_api.service;

import com.sundaymvp.invoice_api.dto.response.CustomerResponse;
import com.sundaymvp.invoice_api.entity.Customer;
import com.sundaymvp.invoice_api.exception.ResourceNotFoundException;
import com.sundaymvp.invoice_api.mapper.CustomerMapper;
import com.sundaymvp.invoice_api.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public CustomerResponse getCustomerById(Long id) {

        Objects.requireNonNull(id, "Customer id must not be null");

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        return CustomerMapper.toResponse(customer);
    }

    public Customer saveCustomer(Customer customer) {

        Objects.requireNonNull(customer, "Customer must not be null");

        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {

        Objects.requireNonNull(id, "Customer id must not be null");
        Objects.requireNonNull(updatedCustomer, "Updated customer must not be null");

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        customer.setName(updatedCustomer.getName());
        customer.setEmail(updatedCustomer.getEmail());
        customer.setPhone(updatedCustomer.getPhone());
        customer.setAddress(updatedCustomer.getAddress());

        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {

        Objects.requireNonNull(id, "Customer id must not be null");

        customerRepository.deleteById(id);
    }
}