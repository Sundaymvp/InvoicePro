package com.sundaymvp.invoice_api.mapper;

import com.sundaymvp.invoice_api.dto.request.CustomerRequest;
import com.sundaymvp.invoice_api.dto.response.CustomerResponse;
import com.sundaymvp.invoice_api.entity.Customer;

public class CustomerMapper {

    private CustomerMapper() {
    }

    public static Customer toEntity(CustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());
        return customer;
    }

    public static CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAddress()
        );
    }
}