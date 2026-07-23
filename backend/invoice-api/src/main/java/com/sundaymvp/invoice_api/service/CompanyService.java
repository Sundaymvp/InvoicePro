package com.sundaymvp.invoice_api.service;

import com.sundaymvp.invoice_api.audit.Audit;
import com.sundaymvp.invoice_api.dto.request.CompanyRequest;
import com.sundaymvp.invoice_api.dto.response.CompanyResponse;
import com.sundaymvp.invoice_api.entity.Company;
import com.sundaymvp.invoice_api.exception.DeleteNotAllowedException;
import com.sundaymvp.invoice_api.exception.ResourceNotFoundException;
import com.sundaymvp.invoice_api.mapper.CompanyMapper;
import com.sundaymvp.invoice_api.repository.CompanyRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SuppressWarnings("null")
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final FileStorageService fileStorageService;

    public CompanyService(
            CompanyRepository companyRepository,
            FileStorageService fileStorageService) {

        this.companyRepository = companyRepository;
        this.fileStorageService = fileStorageService;
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
    @Audit(
        module = "Company",
        action = "CREATE"
)
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
    @Audit(
        module = "Company",
        action = "UPDATE"
)
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
     * Upload company logo
     */
    @Audit(
            module = "Company",
            action = "UPLOAD LOGO"
    )
    public CompanyResponse uploadLogo(
            Long companyId,
            MultipartFile file) {

        Objects.requireNonNull(
                companyId,
                "Company id must not be null");

        Objects.requireNonNull(
                file,
                "File must not be null");

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Company not found"));

        // Delete old logo if it exists
        if (company.getLogo() != null
                && !company.getLogo().isBlank()) {

            fileStorageService.deleteFile(
                    "company-logos",
                    company.getLogo());
        }

        // Upload new logo
        String filename = fileStorageService.uploadFile(
                file,
                "company-logos");

        company.setLogo(filename);

        Company savedCompany = companyRepository.save(company);

        return CompanyMapper.toResponse(savedCompany);
    }

    /**
     * Delete company
     */
    @Audit(
        module = "Company",
        action = "DELETE"
)
    public void deleteCompany(Long id) {

    Company company = companyRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Company not found"));

    try {
        companyRepository.delete(company);
    } catch (DataIntegrityViolationException ex) {
        throw new DeleteNotAllowedException(
                "Cannot delete company because it has related records.");
    }
    }
}