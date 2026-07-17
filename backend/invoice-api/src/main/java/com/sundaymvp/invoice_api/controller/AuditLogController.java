package com.sundaymvp.invoice_api.controller;

import com.sundaymvp.invoice_api.dto.response.AuditLogResponse;
import com.sundaymvp.invoice_api.service.AuditLogService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {

    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    /**
     * Get all audit logs
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<AuditLogResponse> getAllAuditLogs() {

        return auditLogService.getAllAuditLogs();
    }

    /**
     * Get audit logs by module
     * Example:
     * GET /api/audit-logs/module/Product
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/module/{module}")
    public List<AuditLogResponse> getAuditLogsByModule(
            @PathVariable String module) {

        return auditLogService.getAuditLogsByModule(module);
    }

    /**
     * Get audit logs by action
     * Example:
     * GET /api/audit-logs/action/CREATE
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/action/{action}")
    public List<AuditLogResponse> getAuditLogsByAction(
            @PathVariable String action) {

        return auditLogService.getAuditLogsByAction(action);
    }

    /**
     * Get audit logs by user
     * Example:
     * GET /api/audit-logs/user/1
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/{userId}")
    public List<AuditLogResponse> getAuditLogsByUser(
            @PathVariable Long userId) {

        return auditLogService.getAuditLogsByUser(userId);
    }
}