package com.petshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.controller.TransactionController;
import com.petshop.dto.TransactionDTO;
import com.petshop.dto.TransactionRequestDTO;
import com.petshop.dto.TransactionStatusUpdateDTO;
import com.petshop.entity.Transaction;
import com.petshop.security.JwtFilter;
import com.petshop.security.JwtUtil;
import com.petshop.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    // Security Mocks to prevent context loading failure
    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private JwtFilter jwtFilter;

    @Autowired
    private ObjectMapper objectMapper;

    private TransactionDTO transactionDTO;

    @BeforeEach
    void setUp() {
        transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionId(101);
        transactionDTO.setAmount(150.0);
        transactionDTO.setStatus("PENDING");
        transactionDTO.setCustomerName("John Doe");
        // transactionDTO.setTimestamp(LocalDateTime.now()); // If your DTO includes this
    }

    // ================= ORDER TESTS =================

    @Test
    void createOrder_success() throws Exception {
        TransactionRequestDTO request = new TransactionRequestDTO();
        request.setCustomerId(1);
        request.setPetId(5);
        request.setTotalAmount(150.0);

        when(transactionService.placeOrder(any(TransactionRequestDTO.class))).thenReturn(transactionDTO);

        mockMvc.perform(post("/transactions/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transactionId").value(101))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void getOrder_success() throws Exception {
        when(transactionService.getOrder(101)).thenReturn(transactionDTO);

        mockMvc.perform(get("/transactions/orders/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value(101))
                .andExpect(jsonPath("$.customerName").value("John Doe"));
    }

    // ================= TRANSACTION TESTS =================

    @Test
    void getAllTransactions_success() throws Exception {
        when(transactionService.getAllTransactions()).thenReturn(List.of(transactionDTO));

        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].transactionId").value(101));
    }

    @Test
    void updateStatus_success() throws Exception {
        TransactionStatusUpdateDTO updateDTO = new TransactionStatusUpdateDTO();

        // FIX: Use the Enum constant instead of a String
        updateDTO.setStatus(Transaction.Status.Success);

        TransactionDTO updatedResponse = new TransactionDTO();
        updatedResponse.setTransactionId(101);
        updatedResponse.setStatus("COMPLETED"); // This stays a String if TransactionDTO uses String

        when(transactionService.updateStatus(anyInt(), any(TransactionStatusUpdateDTO.class)))
                .thenReturn(updatedResponse);

        mockMvc.perform(put("/transactions/101/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @Test
    void getCustomerTransactions_success() throws Exception {
        when(transactionService.getTransactionsByCustomer(1)).thenReturn(List.of(transactionDTO));

        mockMvc.perform(get("/transactions/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].customerName").value("John Doe"));
    }
}
