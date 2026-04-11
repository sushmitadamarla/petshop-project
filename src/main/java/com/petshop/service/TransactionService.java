package com.petshop.service;

import com.petshop.dto.TransactionDTO;
import com.petshop.entity.Transaction;
import com.petshop.exception.ResourceNotFoundException;
import com.petshop.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repo;

    // CREATE TRANSACTION (STATIC PAYMENT)
    public Transaction createTransaction(Transaction t) {

        // static logic: if amount < 10000 → success else failed
        if (t.getAmount() < 10000) {
            t.setStatus(Transaction.Status.Success);
        } else {
            t.setStatus(Transaction.Status.Failed);
        }

        t.setTransactionDate(LocalDate.now());

        return repo.save(t);
    }

    // GET ONE
    public TransactionDTO getTransaction(int id) {
        Transaction t = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        return new TransactionDTO(
                t.getId(),
                t.getCustomerId(),
                t.getPetId(),
                t.getTransactionDate(),
                t.getAmount(),
                t.getStatus().name()
        );
    }

    // GET ALL
    public List<TransactionDTO> getAllTransactions() {
        return repo.findAll().stream().map(t ->
                new TransactionDTO(
                        t.getId(),
                        t.getCustomerId(),
                        t.getPetId(),
                        t.getTransactionDate(),
                        t.getAmount(),
                        t.getStatus().name()
                )
        ).toList();
    }
}