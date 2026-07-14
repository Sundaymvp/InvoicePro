package com.sundaymvp.invoice_api.repository;

import com.sundaymvp.invoice_api.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findFirstByOrderByIdAsc();

    boolean existsByEmail(String email);

    boolean existsByCompanyName(String companyName);

}