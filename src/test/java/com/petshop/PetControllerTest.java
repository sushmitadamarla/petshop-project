package com.petshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.controller.PetController;
import com.petshop.dto.PetDTO;
import com.petshop.dto.PetDetailsDTO;
import com.petshop.dto.PetRequestDTO;
import com.petshop.entity.Pet;
import com.petshop.security.JwtFilter;
import com.petshop.security.JwtUtil;
import com.petshop.service.PetService;
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

@WebMvcTest(controllers = PetController.class)
@AutoConfigureMockMvc(addFilters = false)
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetService petService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private JwtFilter jwtFilter;

    @Autowired
    private ObjectMapper objectMapper;

    // ================= ADD PET =================
    @Test
    void addPet_success() throws Exception {
        // FIX: Added categoryId, breed, and price to pass @Valid constraints
        PetRequestDTO request = new PetRequestDTO();
        request.setName("Max");
        request.setBreed("Beagle");
        request.setAge(2);
        request.setPrice(300.0);
        request.setCategoryId(1); // Added this

        Pet savedPet = new Pet();
        savedPet.setPetId(1);
        savedPet.setName("Max");

        when(petService.addPet(any(PetRequestDTO.class))).thenReturn(savedPet);

        mockMvc.perform(post("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.petId").value(1))
                .andExpect(jsonPath("$.name").value("Max"));
    }

    // ================= GET ALL PETS =================
    @Test
    void getAllPets_success() throws Exception {
        PetDTO petDto = new PetDTO(1, "Buddy", "Golden Retriever", 3, 500.0);

        when(petService.getAllPets()).thenReturn(List.of(petDto));

        mockMvc.perform(get("/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].petId").value(1))
                .andExpect(jsonPath("$[0].name").value("Buddy"));
    }

    // ================= GET PET BY ID =================
    @Test
    void getPetById_success() throws Exception {
        Pet pet = new Pet();
        pet.setPetId(5);
        pet.setName("Charlie");

        when(petService.getPetById(5)).thenReturn(pet);

        mockMvc.perform(get("/pets/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.petId").value(5));
    }

    // ================= UPDATE PET =================
    @Test
    void updatePet_success() throws Exception {
        // FIX: Added all missing fields to pass the 400 error validation
        PetRequestDTO request = new PetRequestDTO();
        request.setName("Rocky");
        request.setBreed("Bulldog");   // Added this
        request.setPrice(450.0);       // Added this
        request.setCategoryId(2);      // Added this
        request.setAge(4);

        Pet updatedPet = new Pet();
        updatedPet.setPetId(1);
        updatedPet.setName("Rocky");

        when(petService.updatePet(anyInt(), any(PetRequestDTO.class))).thenReturn(updatedPet);

        mockMvc.perform(put("/pets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Rocky"));
    }

    // ================= GET PET DETAILS =================
    @Test
    void getPetDetails_success() throws Exception {
        PetDetailsDTO details = new PetDetailsDTO(
                1, "Buddy", "Retriever", 3, 500.0, "Active dog", "buddy.jpg", "Dogs"
        );

        when(petService.getPetDetails(1)).thenReturn(details);

        mockMvc.perform(get("/pets/1/details"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.petId").value(1))
                .andExpect(jsonPath("$.categoryName").value("Dogs"));
    }
}