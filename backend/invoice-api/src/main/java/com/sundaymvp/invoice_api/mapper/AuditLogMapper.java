package com.sundaymvp.invoice_api.mapper;

import com.sundaymvp.invoice_api.dto.response.AuditLogResponse;
import com.sundaymvp.invoice_api.entity.AuditLog;

public class AuditLogMapper {

    private AuditLogMapper() {
    }

    public static AuditLogResponse toResponse(AuditLog auditLog) {

        String userName = "";

        if (auditLog.getUser() != null) {
            userName = auditLog.getUser().getFirstName() + " "
                    + auditLog.getUser().getLastName();
        }

        return new AuditLogResponse(
                auditLog.getId(),
                auditLog.getUser() != null ? auditLog.getUser().getId() : null,
                userName,
                auditLog.getModule(),
                auditLog.getAction(),
                auditLog.getDescription(),
                auditLog.getIpAddress(),
                auditLog.getCreatedAt()
        );
    }
}