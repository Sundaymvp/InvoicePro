package com.sundaymvp.invoice_api.controller;

import com.sundaymvp.invoice_api.dto.request.CompanyRequest;
import com.sundaymvp.invoice_api.dto.response.CompanyResponse;
import com.sundaymvp.invoice_api.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin(origins = "*")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * Get all companies
     */
    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAllCompanies() {

        return ResponseEntity.ok(
                companyService.getAllCompanies());
    }

    /**
     * Get company by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompanyById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                companyService.getCompanyById(id));
    }

    /**
     * Get current company
     */
    @GetMapping("/current")
    public ResponseEntity<CompanyResponse> getCurrentCompany() {

        return ResponseEntity.ok(
                companyService.getCurrentCompany());
    }

    /**
     * Create company
     */
    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(
            @Valid @RequestBody CompanyRequest request) {

        return new ResponseEntity<>(
                companyService.saveCompany(request),
                HttpStatus.CREATED);
    }

    /**
     * Update company
     */
    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> updateCompany(
            @PathVariable Long id,
            @Valid @RequestBody CompanyRequest request) {

        return ResponseEntity.ok(
                companyService.updateCompany(id, request));
    }

    /**
     * Delete company
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(
            @PathVariable Long id) {

        companyService.deleteCompany(id);

        return ResponseEntity.ok(
                "Company deleted successfully.");
    }
}