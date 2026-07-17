package com.sundaymvp.invoice_api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * User that performed the action
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Module affected
     * Example: Customer, Product, Invoice
     */
    @Column(nullable = false)
    private String module;

    /**
     * Action performed
     * CREATE, UPDATE, DELETE, LOGIN, EMAIL
     */
    @Column(nullable = false)
    private String action;

    /**
     * Human-readable description
     */
    @Column(nullable = false, length = 1000)
    private String description;

    /**
     * Client IP Address
     */
    private String ipAddress;

    /**
     * Timestamp
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    public AuditLog() {
    }

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public AuditLog(User user,
                    String module,
                    String action,
                    String description,
                    String ipAddress) {

        this.user = user;
        this.module = module;
        this.action = action;
        this.description = description;
        this.ipAddress = ipAddress;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}