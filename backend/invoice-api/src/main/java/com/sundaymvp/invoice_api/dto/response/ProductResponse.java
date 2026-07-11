package com.sundaymvp.invoice_api.dto.response;

public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private String category;
    private String sku;
    private String barcode;
    private Double costPrice;
    private Double sellingPrice;
    private Integer quantity;
    private String unit;
    private Boolean status;

    public ProductResponse() {
    }

    public ProductResponse(Long id, String name, String description,
                           String category, String sku, String barcode,
                           Double costPrice, Double sellingPrice,
                           Integer quantity, String unit,
                           Boolean status) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.sku = sku;
        this.barcode = barcode;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.unit = unit;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}