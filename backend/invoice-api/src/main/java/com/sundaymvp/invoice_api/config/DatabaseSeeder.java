package com.sundaymvp.invoice_api.config;

import com.sundaymvp.invoice_api.entity.Company;
import com.sundaymvp.invoice_api.entity.Role;
import com.sundaymvp.invoice_api.enums.RoleName;
import com.sundaymvp.invoice_api.repository.CompanyRepository;
import com.sundaymvp.invoice_api.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final CompanyRepository companyRepository;
    private final RoleRepository roleRepository;

    public DatabaseSeeder(CompanyRepository companyRepository,
                          RoleRepository roleRepository) {
        this.companyRepository = companyRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {

        // Seed Roles
        if (roleRepository.count() == 0) {

            roleRepository.save(new Role(RoleName.ROLE_ADMIN));
            roleRepository.save(new Role(RoleName.ROLE_MANAGER));
            roleRepository.save(new Role(RoleName.ROLE_ACCOUNTANT));
            roleRepository.save(new Role(RoleName.ROLE_SALES));
            roleRepository.save(new Role(RoleName.ROLE_STAFF));

            System.out.println("✅ Default roles created successfully.");
        }

        // Seed Company
        if (companyRepository.count() == 0) {

            Company company = new Company();

            company.setCompanyName("InvoicePro");
            company.setEmail("admin@invoicepro.com");
            company.setPhone("+234000000000");
            company.setWebsite("https://invoicepro.com");
            company.setAddress("Lagos, Nigeria");
            company.setCity("Lagos");
            company.setState("Lagos");
            company.setCountry("Nigeria");
            company.setTaxNumber("N/A");
            company.setRegistrationNumber("N/A");
            company.setCurrency("NGN");
            company.setCurrencySymbol("₦");
            company.setInvoicePrefix("INV-");
            company.setInvoiceFooter("Thank you for your business.");
            company.setBankName("N/A");
            company.setBankAccountName("N/A");
            company.setBankAccountNumber("N/A");
            company.setStatus(true);
            company.setLogo(null);

            companyRepository.save(company);

            System.out.println("✅ Default company created successfully.");
        }
    }
}