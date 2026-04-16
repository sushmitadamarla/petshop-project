package com.petshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.controller.CustomerController;
import com.petshop.dto.CustomerDTO;
import com.petshop.dto.LoginRequest;
import com.petshop.entity.Address;
import com.petshop.security.JwtFilter;
import com.petshop.security.JwtUtil;
import com.petshop.service.CustomerService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

// IMPORTANT: Import Mockito matchers
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CustomerController.class)
@AutoConfigureMockMvc(addFilters = false) // Bypasses Spring Security filters
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService service;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private JwtFilter jwtFilter;

    @Autowired
    private ObjectMapper objectMapper;

    // ================= REGISTER =================
    @Test
    void registerCustomer_success() throws Exception {
        CustomerDTO dto = new CustomerDTO(
                1, "John", "Doe", "john@test.com", "1234567890",
                "Street 1", "Pune", "MH", "411001"
        );

        // FIX: Use any() so Mockito matches the object Jackson creates
        when(service.registerCustomer(any(CustomerDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/customers/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.city").value("Pune"));
    }

    // ================= LOGIN =================
    @Test
    void login_success() throws Exception {
        LoginRequest request = new LoginRequest("john@test.com", "1234567890");

        // FIX: Use any() to ensure "Login successful" is actually returned
        when(service.login(any(LoginRequest.class))).thenReturn("Login successful");

        mockMvc.perform(post("/customers/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Login successful"));
    }

    // ================= GET CUSTOMER =================
    @Test
    void getCustomer_success() throws Exception {
        CustomerDTO dto = new CustomerDTO(
                1, "John", "Doe", "john@test.com", "1234567890",
                "Street 1", "Pune", "MH", "411001"
        );

        when(service.getCustomer(1)).thenReturn(dto);

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john@test.com"));
    }

    // ================= UPDATE CUSTOMER =================
    @Test
    void updateCustomer_success() throws Exception {
        CustomerDTO dto = new CustomerDTO(
                1, "John", "Doe", "john@test.com", "1234567890",
                "Street 1", "Pune", "MH", "411001"
        );

        // FIX: Use anyInt() and any()
        when(service.updateCustomer(anyInt(), any(CustomerDTO.class))).thenReturn(dto);

        mockMvc.perform(put("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    // ================= GET ADDRESS =================
    @Test
    void getAddress_success() throws Exception {
        Address address = new Address();
        address.setId(1);
        address.setCity("Pune");

        when(service.getAddress(1)).thenReturn(address);

        mockMvc.perform(get("/customers/1/address"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Pune"));
    }

    // ================= ADD ADDRESS =================
    @Test
    void addAddress_success() throws Exception {
        Address address = new Address();
        address.setCity("Mumbai");

        // FIX: Use anyInt() and any()
        when(service.addAddress(anyInt(), any(Address.class))).thenReturn(address);

        mockMvc.perform(post("/customers/1/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(address)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.city").value("Mumbai"));
    }
}