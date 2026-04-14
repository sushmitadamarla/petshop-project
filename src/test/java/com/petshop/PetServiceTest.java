package com.petshop;

import com.petshop.dto.PetDTO;
import com.petshop.dto.PetDetailsDTO;
import com.petshop.dto.PetRequestDTO;
import com.petshop.entity.Pet;
import com.petshop.entity.PetCategory;
import com.petshop.exception.ResourceNotFoundException;
import com.petshop.repository.PetCategoryRepository;
import com.petshop.repository.PetRepository;
import com.petshop.service.PetService;
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
class PetServiceTest {

    @Mock
    private PetRepository petRepo;

    @Mock
    private PetCategoryRepository categoryRepo;

    @InjectMocks
    private PetService petService;

    private PetCategory category;
    private Pet pet;
    private PetRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        category = new PetCategory();
        category.setCategoryId(1);
        category.setName("Dogs");

        pet = new Pet();
        pet.setPetId(1);
        pet.setName("Buddy");
        pet.setBreed("Labrador");
        pet.setAge(2);
        pet.setPrice(500.0);
        pet.setDescription("Friendly dog");
        pet.setImageUrl("buddy.jpg");
        pet.setCategory(category);

        requestDTO = new PetRequestDTO();
        requestDTO.setName("Buddy");
        requestDTO.setBreed("Labrador");
        requestDTO.setAge(2);
        requestDTO.setPrice(500.0);
        requestDTO.setDescription("Friendly dog");
        requestDTO.setImageUrl("buddy.jpg");
        requestDTO.setCategoryId(1);
    }

    // ── addPet ────────────────────────────────────────────────────────────

    @Test
    void addPet_success() {
        when(categoryRepo.findById(1)).thenReturn(Optional.of(category));
        when(petRepo.save(any(Pet.class))).thenReturn(pet);

        Pet result = petService.addPet(requestDTO);

        assertThat(result.getName()).isEqualTo("Buddy");
        assertThat(result.getBreed()).isEqualTo("Labrador");
        assertThat(result.getCategory().getName()).isEqualTo("Dogs");
        verify(petRepo, times(1)).save(any(Pet.class));
    }

    @Test
    void addPet_categoryNotFound_throwsException() {
        when(categoryRepo.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> petService.addPet(requestDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Category not found");

        verify(petRepo, never()).save(any());
    }

    // ── getAllPets ─────────────────────────────────────────────────────────

    @Test
    void getAllPets_returnsListOfDTOs() {
        when(petRepo.findAll()).thenReturn(List.of(pet));

        List<PetDTO> result = petService.getAllPets();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Buddy");
        assertThat(result.get(0).getBreed()).isEqualTo("Labrador");
    }

    @Test
    void getAllPets_emptyList() {
        when(petRepo.findAll()).thenReturn(List.of());

        List<PetDTO> result = petService.getAllPets();

        assertThat(result).isEmpty();
    }

    // ── getPetById ────────────────────────────────────────────────────────

    @Test
    void getPetById_success() {
        when(petRepo.findById(1)).thenReturn(Optional.of(pet));

        Pet result = petService.getPetById(1);

        assertThat(result.getPetId()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("Buddy");
    }

    @Test
    void getPetById_notFound_throwsException() {
        when(petRepo.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> petService.getPetById(99))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Pet not found with id: 99");
    }

    // ── getPetsByCategory ─────────────────────────────────────────────────

    @Test
    void getPetsByCategory_success() {
        when(categoryRepo.existsById(1)).thenReturn(true);
        when(petRepo.findByCategory_CategoryId(1)).thenReturn(List.of(pet));

        List<Pet> result = petService.getPetsByCategory(1);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Buddy");
    }

    @Test
    void getPetsByCategory_categoryNotFound_throwsException() {
        when(categoryRepo.existsById(99)).thenReturn(false);

        assertThatThrownBy(() -> petService.getPetsByCategory(99))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Category not found with id: 99");
    }

    // ── updatePet ─────────────────────────────────────────────────────────

    @Test
    void updatePet_success() {
        requestDTO.setName("Max");
        requestDTO.setBreed("Poodle");

        Pet updatedPet = new Pet();
        updatedPet.setPetId(1);
        updatedPet.setName("Max");
        updatedPet.setBreed("Poodle");
        updatedPet.setCategory(category);

        when(petRepo.findById(1)).thenReturn(Optional.of(pet));
        when(categoryRepo.findById(1)).thenReturn(Optional.of(category));
        when(petRepo.save(any(Pet.class))).thenReturn(updatedPet);

        Pet result = petService.updatePet(1, requestDTO);

        assertThat(result.getName()).isEqualTo("Max");
        assertThat(result.getBreed()).isEqualTo("Poodle");
        verify(petRepo, times(1)).save(any(Pet.class));
    }

    @Test
    void updatePet_petNotFound_throwsException() {
        when(petRepo.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> petService.updatePet(99, requestDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Pet not found with id: 99");
    }

    @Test
    void updatePet_categoryNotFound_throwsException() {
        when(petRepo.findById(1)).thenReturn(Optional.of(pet));
        when(categoryRepo.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> petService.updatePet(1, requestDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Category not found");
    }

    // ── getPetDetails ─────────────────────────────────────────────────────

    @Test
    void getPetDetails_success() {
        when(petRepo.findById(1)).thenReturn(Optional.of(pet));

        PetDetailsDTO result = petService.getPetDetails(1);

        assertThat(result.getPetId()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("Buddy");
        assertThat(result.getCategoryName()).isEqualTo("Dogs");
    }

    @Test
    void getPetDetails_noCategoryReturnsNull() {
        pet.setCategory(null);
        when(petRepo.findById(1)).thenReturn(Optional.of(pet));

        PetDetailsDTO result = petService.getPetDetails(1);

        assertThat(result.getCategoryName()).isNull();
    }

    @Test
    void getPetDetails_notFound_throwsException() {
        when(petRepo.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> petService.getPetDetails(99))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Pet not found");
    }
}