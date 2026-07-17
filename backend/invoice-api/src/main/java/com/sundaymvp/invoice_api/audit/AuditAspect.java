package com.sundaymvp.invoice_api.audit;

import com.sundaymvp.invoice_api.entity.User;
import com.sundaymvp.invoice_api.service.AuditLogService;
import com.sundaymvp.invoice_api.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {

    private final AuditLogService auditLogService;
    private final UserService userService;
    private final HttpServletRequest request;

    public AuditAspect(
            AuditLogService auditLogService,
            UserService userService,
            HttpServletRequest request) {

        this.auditLogService = auditLogService;
        this.userService = userService;
        this.request = request;
    }

    @AfterReturning("@annotation(audit)")
    public void logAudit(
            JoinPoint joinPoint,
            Audit audit) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return;
        }

        String email = authentication.getName();

        User user = userService.findByEmail(email);

        String ipAddress = request.getRemoteAddr();

        String description =
                audit.action() + " " +
                audit.module() +
                " successfully";

        auditLogService.logAction(
                user.getId(),
                audit.module(),
                audit.action(),
                description,
                ipAddress
        );
    }
}