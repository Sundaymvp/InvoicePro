package com.sundaymvp.invoice_api.mapper;

import com.sundaymvp.invoice_api.dto.request.SupplierRequest;
import com.sundaymvp.invoice_api.dto.response.SupplierResponse;
import com.sundaymvp.invoice_api.entity.Supplier;

public class SupplierMapper {

    private SupplierMapper() {
    }

    public static Supplier toEntity(SupplierRequest request) {

        Supplier supplier = new Supplier();

        supplier.setName(request.getName());
        supplier.setContactPerson(request.getContactPerson());
        supplier.setEmail(request.getEmail());
        supplier.setPhone(request.getPhone());
        supplier.setAddress(request.getAddress());
        supplier.setCity(request.getCity());
        supplier.setState(request.getState());
        supplier.setCountry(request.getCountry());
        supplier.setStatus(request.getStatus());

        return supplier;
    }

    public static SupplierResponse toResponse(Supplier supplier) {

        return new SupplierResponse(
                supplier.getId(),
                supplier.getName(),
                supplier.getContactPerson(),
                supplier.getEmail(),
                supplier.getPhone(),
                supplier.getAddress(),
                supplier.getCity(),
                supplier.getState(),
                supplier.getCountry(),
                supplier.getStatus()
        );
    }
}