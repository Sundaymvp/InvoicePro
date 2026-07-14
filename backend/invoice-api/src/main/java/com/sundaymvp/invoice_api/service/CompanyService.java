package com.sundaymvp.invoice_api.service;

import com.sundaymvp.invoice_api.dto.request.CompanyRequest;
import com.sundaymvp.invoice_api.dto.response.CompanyResponse;
import com.sundaymvp.invoice_api.entity.Company;
import com.sundaymvp.invoice_api.exception.ResourceNotFoundException;
import com.sundaymvp.invoice_api.mapper.CompanyMapper;
import com.sundaymvp.invoice_api.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SuppressWarnings("null")
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(
            CompanyRepository companyRepository) {

        this.companyRepository = companyRepository;
    }

    /**
     * Get all companies
     */
    public List<CompanyResponse> getAllCompanies() {

        return companyRepository.findAll()
                .stream()
                .map(CompanyMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get company by id
     */
    public CompanyResponse getCompanyById(Long id) {

        Objects.requireNonNull(id, "Company id must not be null");

        Company company = companyRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found"));

        return CompanyMapper.toResponse(company);
    }

    /**
     * Get current company.
     * MVP Invoice currently uses a single company.
     */
    public CompanyResponse getCurrentCompany() {

        Company company = companyRepository.findById(1L)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found"));

        return CompanyMapper.toResponse(company);
    }

    /**
     * Create company
     */
    public CompanyResponse saveCompany(CompanyRequest request) {

        Objects.requireNonNull(request, "Company request must not be null");

        Company company = new Company();

        company.setCompanyName(request.getCompanyName());
        company.setLogo(request.getLogo());
        company.setEmail(request.getEmail());
        company.setPhone(request.getPhone());
        company.setWebsite(request.getWebsite());

        company.setAddress(request.getAddress());
        company.setCity(request.getCity());
        company.setState(request.getState());
        company.setCountry(request.getCountry());

        company.setTaxNumber(request.getTaxNumber());
        company.setRegistrationNumber(request.getRegistrationNumber());

        company.setCurrency(request.getCurrency());
        company.setCurrencySymbol(request.getCurrencySymbol());

        company.setInvoicePrefix(request.getInvoicePrefix());
        company.setInvoiceFooter(request.getInvoiceFooter());

        company.setBankName(request.getBankName());
        company.setBankAccountName(request.getBankAccountName());
        company.setBankAccountNumber(request.getBankAccountNumber());

        company.setStatus(request.getStatus());

        return CompanyMapper.toResponse(
                companyRepository.save(company));
    }

    /**
     * Update company
     */
    public CompanyResponse updateCompany(
            Long id,
            CompanyRequest request) {

        Objects.requireNonNull(id, "Company id must not be null");

        Objects.requireNonNull(request,
                "Company request must not be null");

        Company company = companyRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found"));

        company.setCompanyName(request.getCompanyName());
        company.setLogo(request.getLogo());
        company.setEmail(request.getEmail());
        company.setPhone(request.getPhone());
        company.setWebsite(request.getWebsite());

        company.setAddress(request.getAddress());
        company.setCity(request.getCity());
        company.setState(request.getState());
        company.setCountry(request.getCountry());

        company.setTaxNumber(request.getTaxNumber());
        company.setRegistrationNumber(request.getRegistrationNumber());

        company.setCurrency(request.getCurrency());
        company.setCurrencySymbol(request.getCurrencySymbol());

        company.setInvoicePrefix(request.getInvoicePrefix());
        company.setInvoiceFooter(request.getInvoiceFooter());

        company.setBankName(request.getBankName());
        company.setBankAccountName(request.getBankAccountName());
        company.setBankAccountNumber(request.getBankAccountNumber());

        company.setStatus(request.getStatus());

        return CompanyMapper.toResponse(
                companyRepository.save(company));
    }

    /**
     * Delete company
     */
    public void deleteCompany(Long id) {

        Objects.requireNonNull(id, "Company id must not be null");

        Company company = companyRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found"));

        companyRepository.delete(company);
    }
}