package com.petshop;

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
import com.petshop.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepo;

    @Mock
    private CustomerRepository customerRepo;

    @Mock
    private PetRepository petRepo;

    @InjectMocks
    private TransactionService service;

    private Customer customer;
    private Pet pet;
    private Transaction transaction;

    @BeforeEach
    void setup() {
        customer = new Customer();
        customer.setId(1);
        customer.setFirstName("John");
        customer.setLastName("Doe");

        pet = new Pet();
        pet.setPetId(1);
        pet.setName("Buddy");

        transaction = new Transaction();
        transaction.setTransactionId(1);
        transaction.setCustomer(customer);
        transaction.setPet(pet);
        transaction.setAmount(5000);
        transaction.setStatus(Transaction.Status.Success);
    }

    // add place order
    // ================= PLACE ORDER =================

    @Test
    void placeOrder_success() {
        TransactionRequestDTO req = new TransactionRequestDTO();
        req.setCustomerId(1);
        req.setPetId(1);
        req.setTotalAmount(5000.0);

        when(customerRepo.findById(1)).thenReturn(Optional.of(customer));
        when(petRepo.findById(1)).thenReturn(Optional.of(pet));
        when(transactionRepo.save(any(Transaction.class))).thenReturn(transaction);

        TransactionDTO result = service.placeOrder(req);

        assertNotNull(result);
        assertEquals(1, result.getCustomerId());
        assertEquals("Buddy", result.getPetName());
        assertEquals("Success", result.getStatus());

        verify(transactionRepo).save(any(Transaction.class));
    }

    @Test
    void placeOrder_customerNotFound() {
        TransactionRequestDTO req = new TransactionRequestDTO();
        req.setCustomerId(1);
        req.setPetId(1);

        when(customerRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.placeOrder(req));
    }

    @Test
    void placeOrder_petNotFound() {
        TransactionRequestDTO req = new TransactionRequestDTO();
        req.setCustomerId(1);
        req.setPetId(1);

        when(customerRepo.findById(1)).thenReturn(Optional.of(customer));
        when(petRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.placeOrder(req));
    }

    @Test
    void placeOrder_failedStatus_whenAmountHigh() {
        TransactionRequestDTO req = new TransactionRequestDTO();
        req.setCustomerId(1);
        req.setPetId(1);
        req.setTotalAmount(15000.0);

        transaction.setStatus(Transaction.Status.Failed);

        when(customerRepo.findById(1)).thenReturn(Optional.of(customer));
        when(petRepo.findById(1)).thenReturn(Optional.of(pet));
        when(transactionRepo.save(any(Transaction.class))).thenReturn(transaction);

        TransactionDTO result = service.placeOrder(req);

        assertEquals("Failed", result.getStatus());
    }

    // ================= GET ORDER =================

    @Test
    void getOrder_success() {
        when(transactionRepo.findById(1)).thenReturn(Optional.of(transaction));

        TransactionDTO result = service.getOrder(1);

        assertEquals(1, result.getTransactionId());
    }

    @Test
    void getOrder_notFound() {
        when(transactionRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getOrder(1));
    }

    // ================= GET TRANSACTION =================

    @Test
    void getTransaction_success() {
        when(transactionRepo.findById(1)).thenReturn(Optional.of(transaction));

        TransactionDTO result = service.getTransaction(1);

        assertEquals(1, result.getTransactionId());
    }

    // ================= GET BY CUSTOMER =================

    @Test
    void getOrdersByCustomer_success() {
        when(customerRepo.existsById(1)).thenReturn(true);
        when(transactionRepo.findByCustomer_Id(1))
                .thenReturn(List.of(transaction));

        List<TransactionDTO> result = service.getOrdersByCustomer(1);

        assertEquals(1, result.size());
    }

    @Test
    void getOrdersByCustomer_customerNotFound() {
        when(customerRepo.existsById(1)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> service.getOrdersByCustomer(1));
    }

    @Test
    void getTransactionsByCustomer_success() {
        when(customerRepo.existsById(1)).thenReturn(true);
        when(transactionRepo.findByCustomer_Id(1))
                .thenReturn(List.of(transaction));

        List<TransactionDTO> result = service.getTransactionsByCustomer(1);

        assertEquals(1, result.size());
    }

    // ================= GET ALL =================

    @Test
    void getAllTransactions_success() {
        when(transactionRepo.findAll()).thenReturn(List.of(transaction));

        List<TransactionDTO> result = service.getAllTransactions();

        assertEquals(1, result.size());
    }

    // ================= UPDATE STATUS =================

    @Test
    void updateStatus_success() {
        TransactionStatusUpdateDTO dto = new TransactionStatusUpdateDTO();
        dto.setStatus(Transaction.Status.Success);

        when(transactionRepo.findById(1)).thenReturn(Optional.of(transaction));
        when(transactionRepo.save(any(Transaction.class))).thenReturn(transaction);

        TransactionDTO result = service.updateStatus(1, dto);

        assertEquals("Success", result.getStatus());
    }

    @Test
    void updateStatus_notFound() {
        TransactionStatusUpdateDTO dto = new TransactionStatusUpdateDTO();

        when(transactionRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.updateStatus(1, dto));
    }

    @Test
    void getTransaction_notFound() {
        when(transactionRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getTransaction(1));
    }

    @Test
    void getTransactionsByCustomer_customerNotFound() {
        when(customerRepo.existsById(1)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> service.getTransactionsByCustomer(1));
    }
}
