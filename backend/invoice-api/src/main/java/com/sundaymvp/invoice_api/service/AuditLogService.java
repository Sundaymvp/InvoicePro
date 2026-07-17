package com.sundaymvp.invoice_api.service;

import com.sundaymvp.invoice_api.dto.response.AuditLogResponse;
import com.sundaymvp.invoice_api.entity.AuditLog;
import com.sundaymvp.invoice_api.entity.User;
import com.sundaymvp.invoice_api.mapper.AuditLogMapper;
import com.sundaymvp.invoice_api.repository.AuditLogRepository;
import com.sundaymvp.invoice_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final UserRepository userRepository;

    public AuditLogService(
            AuditLogRepository auditLogRepository,
            UserRepository userRepository) {

        this.auditLogRepository = auditLogRepository;
        this.userRepository = userRepository;
    }

    /**
     * Save an audit log
     */
    public void logAction(
            Long userId,
            String module,
            String action,
            String description,
            String ipAddress) {

        Objects.requireNonNull(userId, "User id must not be null");

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        AuditLog auditLog = new AuditLog();

        auditLog.setUser(user);
        auditLog.setModule(module);
        auditLog.setAction(action);
        auditLog.setDescription(description);
        auditLog.setIpAddress(ipAddress);

        auditLogRepository.save(auditLog);
    }

    /**
     * Get all audit logs
     */
    public List<AuditLogResponse> getAllAuditLogs() {

        return auditLogRepository.findAll()
                .stream()
                .map(AuditLogMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get audit logs by module
     */
    public List<AuditLogResponse> getAuditLogsByModule(String module) {

        return auditLogRepository.findByModuleIgnoreCase(module)
                .stream()
                .map(AuditLogMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get audit logs by action
     */
    public List<AuditLogResponse> getAuditLogsByAction(String action) {

        return auditLogRepository.findByActionIgnoreCase(action)
                .stream()
                .map(AuditLogMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get audit logs by user
     */
    public List<AuditLogResponse> getAuditLogsByUser(Long userId) {

        return auditLogRepository.findByUser_Id(userId)
                .stream()
                .map(AuditLogMapper::toResponse)
                .collect(Collectors.toList());
    }
}