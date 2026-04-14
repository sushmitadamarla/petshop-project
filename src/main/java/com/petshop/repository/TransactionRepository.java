package com.petshop.repository;

import com.petshop.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    // for GET /api/customers/{id}/transactions
    List<Transaction> findByCustomer_Id(int customerId);
}