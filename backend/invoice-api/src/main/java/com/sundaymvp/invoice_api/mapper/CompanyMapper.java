package com.sundaymvp.invoice_api.mapper;

import com.sundaymvp.invoice_api.dto.response.CompanyResponse;
import com.sundaymvp.invoice_api.entity.Company;

public class CompanyMapper {

    private CompanyMapper() {
    }

    public static CompanyResponse toResponse(Company company) {

        if (company == null) {
            return null;
        }

        return new CompanyResponse(
                company.getId(),
                company.getCompanyName(),
                company.getLogo(),
                company.getEmail(),
                company.getPhone(),
                company.getWebsite(),
                company.getAddress(),
                company.getCity(),
                company.getState(),
                company.getCountry(),
                company.getTaxNumber(),
                company.getRegistrationNumber(),
                company.getCurrency(),
                company.getCurrencySymbol(),
                company.getInvoicePrefix(),
                company.getInvoiceFooter(),
                company.getBankName(),
                company.getBankAccountName(),
                company.getBankAccountNumber(),
                company.getStatus(),
                company.getCreatedAt(),
                company.getUpdatedAt()
        );
    }
}