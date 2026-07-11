package com.sundaymvp.invoice_api.dto.request;

public class InvoiceItemRequest {

    private Long invoiceId;
    private Long productId;
    private Integer quantity;
    private Double unitPrice;
    private Double discount;
    private Double tax;

    public InvoiceItemRequest() {
    }

    public InvoiceItemRequest(Long invoiceId,
                              Long productId,
                              Integer quantity,
                              Double unitPrice,
                              Double discount,
                              Double tax) {

        this.invoiceId = invoiceId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.tax = tax;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }
}