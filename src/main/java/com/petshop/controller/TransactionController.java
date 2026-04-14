package com.petshop.controller;

import com.petshop.dto.TransactionDTO;
import com.petshop.dto.TransactionRequestDTO;
import com.petshop.dto.TransactionStatusUpdateDTO;
import com.petshop.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    // ================= ORDER =================

    @PostMapping("/orders")
    public ResponseEntity<TransactionDTO> createOrder(@Valid @RequestBody TransactionRequestDTO dto) {
        return new ResponseEntity<>(service.placeOrder(dto), HttpStatus.CREATED);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<TransactionDTO> getOrder(@PathVariable int id) {
        return ResponseEntity.ok(service.getOrder(id));
    }

    @GetMapping("/customers/{id}/orders")
    public ResponseEntity<List<TransactionDTO>> getCustomerOrders(@PathVariable int id) {
        return ResponseEntity.ok(service.getOrdersByCustomer(id));
    }

    // ================= TRANSACTION =================

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransaction(@PathVariable int id) {
        return ResponseEntity.ok(service.getTransaction(id));
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<List<TransactionDTO>> getCustomerTransactions(@PathVariable int id) {
        return ResponseEntity.ok(service.getTransactionsByCustomer(id));
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAll() {
        return ResponseEntity.ok(service.getAllTransactions());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<TransactionDTO> updateStatus(@PathVariable int id,
                                                       @Valid @RequestBody TransactionStatusUpdateDTO dto) {
        return ResponseEntity.ok(service.updateStatus(id, dto));
    }
}