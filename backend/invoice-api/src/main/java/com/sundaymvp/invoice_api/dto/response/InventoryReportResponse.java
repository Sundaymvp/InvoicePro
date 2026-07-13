package com.sundaymvp.invoice_api.dto.response;

public class InventoryReportResponse {

    private Long totalProducts;

    private Long totalStockQuantity;

    private Long lowStockProducts;

    private Long outOfStockProducts;

    private Double inventoryValue;

    public InventoryReportResponse() {
    }

    public InventoryReportResponse(
            Long totalProducts,
            Long totalStockQuantity,
            Long lowStockProducts,
            Long outOfStockProducts,
            Double inventoryValue) {

        this.totalProducts = totalProducts;
        this.totalStockQuantity = totalStockQuantity;
        this.lowStockProducts = lowStockProducts;
        this.outOfStockProducts = outOfStockProducts;
        this.inventoryValue = inventoryValue;
    }

    public Long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(Long totalProducts) {
        this.totalProducts = totalProducts;
    }

    public Long getTotalStockQuantity() {
        return totalStockQuantity;
    }

    public void setTotalStockQuantity(Long totalStockQuantity) {
        this.totalStockQuantity = totalStockQuantity;
    }

    public Long getLowStockProducts() {
        return lowStockProducts;
    }

    public void setLowStockProducts(Long lowStockProducts) {
        this.lowStockProducts = lowStockProducts;
    }

    public Long getOutOfStockProducts() {
        return outOfStockProducts;
    }

    public void setOutOfStockProducts(Long outOfStockProducts) {
        this.outOfStockProducts = outOfStockProducts;
    }

    public Double getInventoryValue() {
        return inventoryValue;
    }

    public void setInventoryValue(Double inventoryValue) {
        this.inventoryValue = inventoryValue;
    }
}