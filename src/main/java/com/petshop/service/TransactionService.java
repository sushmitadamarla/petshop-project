package com.petshop.service;

import com.petshop.dto.TransactionDTO;
import com.petshop.dto.TransactionRequestDTO;
import com.petshop.dto.TransactionStatusUpdateDTO;
import com.petshop.entity.Customer;
import com.petshop.entity.Pet;
import com.petshop.entity.Transaction;
import com.petshop.exception.ResourceNotFoundException;
import com.petshop.repository.CustomerRepository;
import com.petshop.repository.PetRepository;
import com.petshop.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private PetRepository petRepo;

    // ── Helper: entity → DTO ──────────────────────────────────────────────
    private TransactionDTO toDTO(Transaction t) {
        return new TransactionDTO(
                t.getTransactionId(),
                t.getCustomer().getId(),
                t.getCustomer().getFirstName() + " " + t.getCustomer().getLastName(),
                t.getPet().getPetId(),
                t.getPet().getName(),
                t.getTransactionDate(),
                t.getAmount(),
                t.getStatus() != null ? t.getStatus().name() : null
        );
    }

    // ── POST /api/orders  (place order = create transaction) ─────────────
    public TransactionDTO placeOrder(TransactionRequestDTO req) {

        Customer customer = customerRepo.findById(req.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Pet pet = petRepo.findById(req.getPetId())
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));

        Transaction t = new Transaction();
        t.setCustomer(customer);
        t.setPet(pet);
        t.setAmount(req.getAmount());
        t.setTransactionDate(LocalDate.now());

        // static payment logic: amount < 10000 → Success, else Failed
        t.setStatus(req.getAmount() < 10000
                ? Transaction.Status.Success
                : Transaction.Status.Failed);

        return toDTO(transactionRepo.save(t));
    }

    // ── GET /api/orders/{id}  ─────────────────────────────────────────────
    public TransactionDTO getOrder(int id) {
        Transaction t = transactionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return toDTO(t);
    }

    // ── GET /api/customers/{id}/orders  ──────────────────────────────────
    public List<TransactionDTO> getOrdersByCustomer(int customerId) {
        if (!customerRepo.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer not found");
        }
        return transactionRepo.findByCustomer_Id(customerId)
                .stream().map(this::toDTO).toList();
    }

    // ── GET /api/transactions/{id}  ───────────────────────────────────────
    public TransactionDTO getTransaction(int id) {
        Transaction t = transactionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        return toDTO(t);
    }

    // ── GET /api/customers/{id}/transactions  ─────────────────────────────
    public List<TransactionDTO> getTransactionsByCustomer(int customerId) {
        if (!customerRepo.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer not found");
        }
        return transactionRepo.findByCustomer_Id(customerId)
                .stream().map(this::toDTO).toList();
    }

    // ── GET /api/transactions  (all)  ─────────────────────────────────────
    public List<TransactionDTO> getAllTransactions() {
        return transactionRepo.findAll().stream().map(this::toDTO).toList();
    }

    // ── PUT /api/transactions/{id}/status  ────────────────────────────────
    public TransactionDTO updateStatus(int id, TransactionStatusUpdateDTO req) {
        Transaction t = transactionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        t.setStatus(req.getStatus());
        return toDTO(transactionRepo.save(t));
    }
}