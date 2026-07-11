package com.sundaymvp.invoice_api.service;

import com.sundaymvp.invoice_api.dto.request.SupplierRequest;
import com.sundaymvp.invoice_api.dto.response.SupplierResponse;
import com.sundaymvp.invoice_api.entity.Supplier;
import com.sundaymvp.invoice_api.exception.ResourceNotFoundException;
import com.sundaymvp.invoice_api.mapper.SupplierMapper;
import com.sundaymvp.invoice_api.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public SupplierResponse getSupplierById(Long id) {

        Objects.requireNonNull(id, "Supplier id must not be null");

        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        return SupplierMapper.toResponse(supplier);
    }

    public Supplier saveSupplier(SupplierRequest request) {

        Objects.requireNonNull(request, "Supplier request must not be null");

        if (supplierRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Supplier email already exists");
        }

        if (supplierRepository.existsByName(request.getName())) {
            throw new RuntimeException("Supplier name already exists");
        }

        Supplier supplier = SupplierMapper.toEntity(request);

        return supplierRepository.save(supplier);
    }

    public Supplier updateSupplier(Long id, SupplierRequest request) {

        Objects.requireNonNull(id, "Supplier id must not be null");
        Objects.requireNonNull(request, "Supplier request must not be null");

        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        supplier.setName(request.getName());
        supplier.setContactPerson(request.getContactPerson());
        supplier.setEmail(request.getEmail());
        supplier.setPhone(request.getPhone());
        supplier.setAddress(request.getAddress());
        supplier.setCity(request.getCity());
        supplier.setState(request.getState());
        supplier.setCountry(request.getCountry());
        supplier.setStatus(request.getStatus());

        return supplierRepository.save(supplier);
    }

    public void deleteSupplier(Long id) {

        Objects.requireNonNull(id, "Supplier id must not be null");

        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        supplierRepository.delete(supplier);
    }
}