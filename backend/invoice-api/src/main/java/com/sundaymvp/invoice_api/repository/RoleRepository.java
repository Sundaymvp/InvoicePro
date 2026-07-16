package com.sundaymvp.invoice_api.repository;

import com.sundaymvp.invoice_api.entity.Role;
import com.sundaymvp.invoice_api.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);

    boolean existsByName(RoleName name);
}