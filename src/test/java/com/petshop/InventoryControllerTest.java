package com.petshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.controller.InventoryController;
import com.petshop.dto.*;
import com.petshop.security.JwtFilter;
import com.petshop.security.JwtUtil;
import com.petshop.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = InventoryController.class)
@AutoConfigureMockMvc(addFilters = false)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService service;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private JwtFilter jwtFilter;

    @Autowired
    private ObjectMapper objectMapper;

    // ================= ASSIGNMENT TESTS =================

    @Test
    void assignEmployee_success() throws Exception {
        when(service.assignEmployee(anyInt(), anyInt())).thenReturn("Employee assigned to pet");

        mockMvc.perform(post("/inventory/pets/1/employees/10"))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee assigned to pet"));
    }

    // ================= FOOD CRUD TESTS =================

    @Test
    void addFood_success() throws Exception {
        // FIX: Added all mandatory fields to pass @Valid check
        PetFoodRequestDTO request = new PetFoodRequestDTO();
        request.setName("Premium Kibble");
        request.setBrand("PetChoice");
        request.setType("Dry Food");
        request.setPrice(29.99);

        PetFoodDTO response = new PetFoodDTO(101, "Premium Kibble", "PetChoice", 29.99);

        when(service.addFood(any(PetFoodRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/inventory/food")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Premium Kibble"));
    }

    // ================= SUPPLIER CRUD TESTS =================

    @Test
    void updateSupplier_success() throws Exception {
        // FIX: Added contactPerson to pass @Valid check
        SupplierRequestDTO request = new SupplierRequestDTO();
        request.setName("Global Pets");
        request.setContactPerson("John Smith");

        SupplierDTO response = new SupplierDTO();
        response.setSupplierId(1);
        response.setName("Global Pets");

        when(service.updateSupplier(anyInt(), any(SupplierRequestDTO.class))).thenReturn(response);

        mockMvc.perform(put("/inventory/suppliers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Global Pets"));
    }

    // ================= EMPLOYEE CRUD TESTS =================

    @Test
    void addEmployee_success() throws Exception {
        // FIX: Added all fields required by @NotBlank and @Pattern
        EmployeeRequestDTO request = new EmployeeRequestDTO();
        request.setFirstName("Alice");
        request.setLastName("Wonderland");
        request.setPosition("Vet");
        request.setPhoneNumber("1234567890");

        EmployeeDTO response = new EmployeeDTO();
        response.setEmployeeId(5);
        response.setFirstName("Alice");

        when(service.addEmployee(any(EmployeeRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/inventory/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Alice"));
    }

    // ================= RELATIONSHIP QUERY TESTS =================

    @Test
    void getEmployeesByPet_success() throws Exception {
        EmployeeDTO employee = new EmployeeDTO();
        employee.setEmployeeId(10);
        employee.setFirstName("Bob");

        when(service.getEmployeesByPet(1)).thenReturn(List.of(employee));

        mockMvc.perform(get("/inventory/pets/1/employees"))
                .andExpect(status().isOk())
                // FIX: Changed "name" to "firstName" to match your DTO
                .andExpect(jsonPath("$[0].firstName").value("Bob"));
    }
}