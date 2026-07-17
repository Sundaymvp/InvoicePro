package com.sundaymvp.invoice_api.repository;

import com.sundaymvp.invoice_api.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByModuleIgnoreCase(String module);

    List<AuditLog> findByActionIgnoreCase(String action);

    List<AuditLog> findByUser_Id(Long userId);

}