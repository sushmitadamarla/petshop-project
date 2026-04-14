package com.petshop;

import com.petshop.entity.PetCategory;
import com.petshop.exception.ResourceNotFoundException;
import com.petshop.repository.PetCategoryRepository;
import com.petshop.service.PetCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetCategoryServiceTest {

    @Mock
    private PetCategoryRepository categoryRepo;

    @InjectMocks
    private PetCategoryService categoryService;

    private PetCategory category;

    @BeforeEach
    void setUp() {
        category = new PetCategory();
        category.setCategoryId(1);
        category.setName("Dogs");
    }

    // ── addCategory ───────────────────────────────────────────────────────

    @Test
    void addCategory_success() {
        when(categoryRepo.save(any(PetCategory.class))).thenReturn(category);

        PetCategory result = categoryService.addCategory(category);

        assertThat(result.getName()).isEqualTo("Dogs");
        verify(categoryRepo, times(1)).save(category);
    }

    // ── getAllCategories ───────────────────────────────────────────────────

    @Test
    void getAllCategories_returnsList() {
        when(categoryRepo.findAll()).thenReturn(List.of(category));

        List<PetCategory> result = categoryService.getAllCategories();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Dogs");
    }

    @Test
    void getAllCategories_emptyList() {
        when(categoryRepo.findAll()).thenReturn(List.of());

        List<PetCategory> result = categoryService.getAllCategories();

        assertThat(result).isEmpty();
    }

    // ── getCategoryById ───────────────────────────────────────────────────

    @Test
    void getCategoryById_success() {
        when(categoryRepo.findById(1)).thenReturn(Optional.of(category));

        PetCategory result = categoryService.getCategoryById(1);

        assertThat(result.getCategoryId()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("Dogs");
    }

    @Test
    void getCategoryById_notFound_throwsException() {
        when(categoryRepo.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.getCategoryById(99))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Category not found");
    }

    // ── updateCategory ────────────────────────────────────────────────────

    @Test
    void updateCategory_success() {
        PetCategory updated = new PetCategory();
        updated.setName("Cats");

        when(categoryRepo.findById(1)).thenReturn(Optional.of(category));
        when(categoryRepo.save(any(PetCategory.class))).thenAnswer(i -> i.getArgument(0));

        PetCategory result = categoryService.updateCategory(1, updated);

        assertThat(result.getName()).isEqualTo("Cats");
        verify(categoryRepo, times(1)).save(any(PetCategory.class));
    }

    @Test
    void updateCategory_notFound_throwsException() {
        when(categoryRepo.findById(99)).thenReturn(Optional.empty());

        PetCategory updated = new PetCategory();
        updated.setName("Cats");

        assertThatThrownBy(() -> categoryService.updateCategory(99, updated))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Category not found");
    }
}