package com.petshop.dto;

import com.petshop.entity.Transaction;
import jakarta.validation.constraints.NotNull;

public class TransactionStatusUpdateDTO {

    @NotNull(message = "Status is required")
    private Transaction.Status status;

    public TransactionStatusUpdateDTO() {}

    public Transaction.Status getStatus() { return status; }
    public void setStatus(Transaction.Status status) { this.status = status; }
}