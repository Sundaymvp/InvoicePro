package com.sundaymvp.invoice_api.repository;

import com.sundaymvp.invoice_api.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    boolean existsByEmail(String email);

    boolean existsByName(String name);

}