package com.petshop.dto;

import java.time.LocalDate;

public class TransactionDTO {

    private int transactionId;
    private int customerId;
    private String customerName;
    private int petId;
    private String petName;
    private LocalDate transactionDate;
    private double amount;
    private String status;

    public TransactionDTO() {}

    public TransactionDTO(int transactionId, int customerId, String customerName,
                          int petId, String petName,
                          LocalDate transactionDate, double amount, String status) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.petId = petId;
        this.petName = petName;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.status = status;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
