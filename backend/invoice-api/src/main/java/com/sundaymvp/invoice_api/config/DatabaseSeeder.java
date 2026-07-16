package com.sundaymvp.invoice_api.config;

import com.sundaymvp.invoice_api.entity.Company;
import com.sundaymvp.invoice_api.entity.Role;
import com.sundaymvp.invoice_api.entity.User;
import com.sundaymvp.invoice_api.enums.RoleName;
import com.sundaymvp.invoice_api.repository.CompanyRepository;
import com.sundaymvp.invoice_api.repository.RoleRepository;
import com.sundaymvp.invoice_api.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final CompanyRepository companyRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(
            CompanyRepository companyRepository,
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.companyRepository = companyRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        // ==========================
        // Seed Roles
        // ==========================
        if (roleRepository.count() == 0) {

            roleRepository.save(new Role(RoleName.ROLE_ADMIN));
            roleRepository.save(new Role(RoleName.ROLE_MANAGER));
            roleRepository.save(new Role(RoleName.ROLE_ACCOUNTANT));
            roleRepository.save(new Role(RoleName.ROLE_SALES));
            roleRepository.save(new Role(RoleName.ROLE_STAFF));

            System.out.println("✅ Default roles created successfully.");
        }

        // ==========================
        // Seed Company
        // ==========================
        Company company;

        if (companyRepository.count() == 0) {

            company = new Company();

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

            company = companyRepository.save(company);

            System.out.println("✅ Default company created successfully.");

        } else {

            company = companyRepository.findFirstByOrderByIdAsc()
                    .orElseThrow(() -> new RuntimeException("Company not found"));
        }

        // ==========================
        // Seed Admin User
        // ==========================
        if (!userRepository.existsByEmail("admin@invoicepro.com")) {

            Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Admin role not found"));

            User admin = new User();

            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setEmail("admin@invoicepro.com");
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setPhone("+234000000000");
            admin.setRole(adminRole);
            admin.setCompany(company);
            admin.setEnabled(true);

            userRepository.save(admin);

            System.out.println("✅ Default admin user created successfully.");
        }
    }
}