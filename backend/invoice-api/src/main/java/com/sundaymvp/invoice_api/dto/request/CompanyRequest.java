package com.sundaymvp.invoice_api.dto.request;

public class CompanyRequest {

    private String companyName;
    private String logo;
    private String email;
    private String phone;
    private String website;

    private String address;
    private String city;
    private String state;
    private String country;

    private String taxNumber;
    private String registrationNumber;

    private String currency;
    private String currencySymbol;

    private String invoicePrefix;
    private String invoiceFooter;

    private String bankName;
    private String bankAccountName;
    private String bankAccountNumber;

    private Boolean status;

    public CompanyRequest() {
    }

    public CompanyRequest(
            String companyName,
            String logo,
            String email,
            String phone,
            String website,
            String address,
            String city,
            String state,
            String country,
            String taxNumber,
            String registrationNumber,
            String currency,
            String currencySymbol,
            String invoicePrefix,
            String invoiceFooter,
            String bankName,
            String bankAccountName,
            String bankAccountNumber,
            Boolean status) {

        this.companyName = companyName;
        this.logo = logo;
        this.email = email;
        this.phone = phone;
        this.website = website;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.taxNumber = taxNumber;
        this.registrationNumber = registrationNumber;
        this.currency = currency;
        this.currencySymbol = currencySymbol;
        this.invoicePrefix = invoicePrefix;
        this.invoiceFooter = invoiceFooter;
        this.bankName = bankName;
        this.bankAccountName = bankAccountName;
        this.bankAccountNumber = bankAccountNumber;
        this.status = status;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getInvoicePrefix() {
        return invoicePrefix;
    }

    public void setInvoicePrefix(String invoicePrefix) {
        this.invoicePrefix = invoicePrefix;
    }

    public String getInvoiceFooter() {
        return invoiceFooter;
    }

    public void setInvoiceFooter(String invoiceFooter) {
        this.invoiceFooter = invoiceFooter;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}