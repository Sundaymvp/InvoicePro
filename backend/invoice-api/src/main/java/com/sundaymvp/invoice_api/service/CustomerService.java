package com.sundaymvp.invoice_api.service;

import com.sundaymvp.invoice_api.dto.response.CustomerResponse;
import com.sundaymvp.invoice_api.entity.Company;
import com.sundaymvp.invoice_api.entity.Customer;
import com.sundaymvp.invoice_api.exception.ResourceNotFoundException;
import com.sundaymvp.invoice_api.mapper.CustomerMapper;
import com.sundaymvp.invoice_api.repository.CompanyRepository;
import com.sundaymvp.invoice_api.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("null")
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;

    public CustomerService(
            CustomerRepository customerRepository,
            CompanyRepository companyRepository) {

        this.customerRepository = customerRepository;
        this.companyRepository = companyRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public CustomerResponse getCustomerById(Long id) {

        Objects.requireNonNull(id, "Customer id must not be null");

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found"));

        return CustomerMapper.toResponse(customer);
    }

    public Customer saveCustomer(Customer customer) {

        Objects.requireNonNull(customer, "Customer must not be null");

        Company company = companyRepository.findById(1L)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found"));

        customer.setCompany(company);

        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {

        Objects.requireNonNull(id, "Customer id must not be null");
        Objects.requireNonNull(updatedCustomer, "Updated customer must not be null");

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found"));

        Company company = companyRepository.findById(1L)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found"));

        customer.setCompany(company);
        customer.setName(updatedCustomer.getName());
        customer.setEmail(updatedCustomer.getEmail());
        customer.setPhone(updatedCustomer.getPhone());
        customer.setAddress(updatedCustomer.getAddress());

        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {

        Objects.requireNonNull(id, "Customer id must not be null");

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found"));

        customerRepository.delete(customer);
    }
}