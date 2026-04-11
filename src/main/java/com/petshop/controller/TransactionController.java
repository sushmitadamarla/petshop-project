package com.petshop.controller;

import com.petshop.dto.TransactionDTO;
import com.petshop.entity.Transaction;
import com.petshop.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    // CREATE
    @PostMapping
    public Transaction create(@RequestBody Transaction t) {
        return service.createTransaction(t);
    }

    // GET ONE
    @GetMapping("/{id}")
    public TransactionDTO getOne(@PathVariable int id) {
        return service.getTransaction(id);
    }

    // GET ALL
    @GetMapping
    public List<TransactionDTO> getAll() {
        return service.getAllTransactions();
    }
}