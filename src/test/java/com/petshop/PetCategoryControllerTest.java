package com.petshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.controller.PetCategoryController;
import com.petshop.entity.PetCategory;
import com.petshop.security.JwtFilter;
import com.petshop.security.JwtUtil;
import com.petshop.service.PetCategoryService;
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

@WebMvcTest(controllers = PetCategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
class PetCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetCategoryService categoryService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private JwtFilter jwtFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addCategory_success() throws Exception {
        PetCategory category = new PetCategory();
        category.setCategoryId(1);
        category.setName("Dogs");

        when(categoryService.addCategory(any(PetCategory.class))).thenReturn(category);

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk())
                // FIX: Changed categoryName to name
                .andExpect(jsonPath("$.name").value("Dogs"));
    }

    @Test
    void getAllCategories_success() throws Exception {
        PetCategory cat1 = new PetCategory();
        cat1.setName("Cats");

        PetCategory cat2 = new PetCategory();
        cat2.setName("Birds");

        when(categoryService.getAllCategories()).thenReturn(List.of(cat1, cat2));

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                // FIX: Changed categoryName to name
                .andExpect(jsonPath("$[0].name").value("Cats"))
                .andExpect(jsonPath("$[1].name").value("Birds"));
    }

    @Test
    void getCategory_success() throws Exception {
        PetCategory category = new PetCategory();
        category.setCategoryId(5);
        category.setName("Fish");

        when(categoryService.getCategoryById(5)).thenReturn(category);

        mockMvc.perform(get("/categories/5"))
                .andExpect(status().isOk())
                // FIX: Changed categoryName to name
                .andExpect(jsonPath("$.name").value("Fish"))
                .andExpect(jsonPath("$.categoryId").value(5));
    }

    @Test
    void updateCategory_success() throws Exception {
        PetCategory category = new PetCategory();
        category.setCategoryId(1);
        category.setName("Reptiles");

        when(categoryService.updateCategory(anyInt(), any(PetCategory.class))).thenReturn(category);

        mockMvc.perform(put("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk())
                // FIX: Changed categoryName to name
                .andExpect(jsonPath("$.name").value("Reptiles"));
    }
}