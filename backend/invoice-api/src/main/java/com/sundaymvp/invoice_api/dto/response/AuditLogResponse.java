package com.sundaymvp.invoice_api.dto.response;

import java.time.LocalDateTime;

public class AuditLogResponse {

    private Long id;
    private Long userId;
    private String userName;
    private String module;
    private String action;
    private String description;
    private String ipAddress;
    private LocalDateTime createdAt;

    public AuditLogResponse() {
    }

    public AuditLogResponse(
            Long id,
            Long userId,
            String userName,
            String module,
            String action,
            String description,
            String ipAddress,
            LocalDateTime createdAt) {

        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.module = module;
        this.action = action;
        this.description = description;
        this.ipAddress = ipAddress;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}