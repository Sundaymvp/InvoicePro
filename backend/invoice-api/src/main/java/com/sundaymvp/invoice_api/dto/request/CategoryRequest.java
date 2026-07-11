package com.sundaymvp.invoice_api.dto.request;

public class CategoryRequest {

    private String name;
    private String description;
    private Boolean status;

    public CategoryRequest() {
    }

    public CategoryRequest(String name, String description, Boolean status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}