package com.petshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.controller.ServiceController;
import com.petshop.dto.GroomingServiceDTO;
import com.petshop.dto.VaccinationDTO;
import com.petshop.security.JwtFilter;
import com.petshop.security.JwtUtil;
import com.petshop.service.ServiceManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ServiceController.class)
@AutoConfigureMockMvc(addFilters = false) // Bypasses security filters
public class ServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceManager serviceManager;

    // Fix for ApplicationContext failure: satisfy JwtFilter's dependency
    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private JwtFilter jwtFilter;

    @Autowired
    private ObjectMapper objectMapper;

    private GroomingServiceDTO groomingDTO;
    private VaccinationDTO vaccinationDTO;

    @BeforeEach
    void setUp() {
        // Initialize with all required fields to satisfy @NotBlank and @NotNull
        groomingDTO = new GroomingServiceDTO(1, "Haircut", "Full grooming", 50.0, true);
        vaccinationDTO = new VaccinationDTO(1, "Rabies", "Annual shot", 30.0, true);
    }

    // ================= TEST ADD SERVICES =================

    @Test
    void testAddGrooming() throws Exception {
        Mockito.when(serviceManager.addGrooming(any(GroomingServiceDTO.class))).thenReturn(groomingDTO);

        mockMvc.perform(post("/services/grooming")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(groomingDTO)))
                .andExpect(status().isCreated())
                // FIX: Field in DTO is "name", not "serviceName"
                .andExpect(jsonPath("$.name").value("Haircut"))
                .andExpect(jsonPath("$.price").value(50.0));
    }

    @Test
    void testAddVaccination() throws Exception {
        Mockito.when(serviceManager.addVaccination(any(VaccinationDTO.class))).thenReturn(vaccinationDTO);

        mockMvc.perform(post("/services/vaccinations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vaccinationDTO)))
                .andExpect(status().isCreated())
                // FIX: Field in DTO is "name", not "vaccineName"
                .andExpect(jsonPath("$.name").value("Rabies"))
                .andExpect(jsonPath("$.price").value(30.0));
    }

    // ================= TEST ASSIGN SERVICES =================

    @Test
    void testAssignGrooming() throws Exception {
        String successMsg = "Grooming assigned successfully";
        Mockito.when(serviceManager.assignGrooming(anyInt(), anyInt())).thenReturn(successMsg);

        mockMvc.perform(post("/services/pets/1/grooming/10"))
                .andExpect(status().isOk())
                .andExpect(content().string(successMsg));
    }

    @Test
    void testAssignVaccination() throws Exception {
        String successMsg = "Vaccination assigned successfully";
        Mockito.when(serviceManager.assignVaccination(anyInt(), anyInt())).thenReturn(successMsg);

        mockMvc.perform(post("/services/pets/1/vaccinations/20"))
                .andExpect(status().isOk())
                .andExpect(content().string(successMsg));
    }

    // ================= TEST GET ALL SERVICES =================

    @Test
    void testGetAllGrooming() throws Exception {
        List<GroomingServiceDTO> list = Arrays.asList(groomingDTO);
        Mockito.when(serviceManager.getAllGrooming()).thenReturn(list);

        mockMvc.perform(get("/services/grooming"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Haircut"));
    }

    // ================= TEST PET HISTORY =================

    @Test
    void testGetPetVaccinationHistory() throws Exception {
        List<VaccinationDTO> history = Arrays.asList(vaccinationDTO);
        Mockito.when(serviceManager.getPetVaccinationHistory(1)).thenReturn(history);

        mockMvc.perform(get("/services/pets/1/vaccinations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Rabies"));
    }
}