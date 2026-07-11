package com.sundaymvp.invoice_api.controller;

import com.sundaymvp.invoice_api.dto.request.SupplierRequest;
import com.sundaymvp.invoice_api.dto.response.SupplierResponse;
import com.sundaymvp.invoice_api.entity.Supplier;
import com.sundaymvp.invoice_api.service.SupplierService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public List<Supplier> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/{id}")
    public SupplierResponse getSupplier(@PathVariable Long id) {
        return supplierService.getSupplierById(id);
    }

    @PostMapping
    public Supplier createSupplier(@RequestBody SupplierRequest request) {
        return supplierService.saveSupplier(request);
    }

    @PutMapping("/{id}")
    public Supplier updateSupplier(
            @PathVariable Long id,
            @RequestBody SupplierRequest request) {

        return supplierService.updateSupplier(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
    }
}