package com.petshop.service;

import com.petshop.dto.PetDTO;
import com.petshop.dto.PetDetailsDTO;
import com.petshop.dto.PetRequestDTO;
import com.petshop.entity.Pet;
import com.petshop.entity.PetCategory;
import com.petshop.exception.ResourceNotFoundException;
import com.petshop.repository.PetCategoryRepository;
import com.petshop.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepo;

    @Autowired
    private PetCategoryRepository categoryRepo;  // needed to resolve category from ID

    // ── POST /pets ────────────────────────────────────────────────────────
    public Pet addPet(PetRequestDTO dto) {

        PetCategory category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Pet pet = new Pet();
        pet.setName(dto.getName());
        pet.setBreed(dto.getBreed());
        pet.setAge(dto.getAge());
        pet.setPrice(dto.getPrice());
        pet.setDescription(dto.getDescription());
        pet.setImageUrl(dto.getImageUrl());
        pet.setCategory(category);

        return petRepo.save(pet);
    }

    // ── GET /pets ─────────────────────────────────────────────────────────
    public List<PetDTO> getAllPets() {
        return petRepo.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ── GET /pets/{id} ────────────────────────────────────────────────────
    public Pet getPetById(int id) {
        return petRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found with id: " + id));
    }

    // ── GET /pets/categories/{categoryId} ─────────────────────────────────
    public List<Pet> getPetsByCategory(int categoryId) {
        if (!categoryRepo.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with id: " + categoryId);
        }
        return petRepo.findByCategory_CategoryId(categoryId);
    }

    // ── PUT /pets/{id} ────────────────────────────────────────────────────
    public Pet updatePet(int id, PetRequestDTO dto) {

        Pet existing = petRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found with id: " + id));

        PetCategory category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        existing.setName(dto.getName());
        existing.setBreed(dto.getBreed());
        existing.setAge(dto.getAge());
        existing.setPrice(dto.getPrice());
        existing.setDescription(dto.getDescription());
        existing.setImageUrl(dto.getImageUrl());
        existing.setCategory(category);

        return petRepo.save(existing);
    }

    // ── DELETE /pets/{id} ─────────────────────────────────────────────────
    public void deletePet(int id) {
        if (!petRepo.existsById(id)) {
            throw new ResourceNotFoundException("Pet not found with id: " + id);
        }
        petRepo.deleteById(id);
    }

    // ── GET /pets/{id}/details ────────────────────────────────────────────
    public PetDetailsDTO getPetDetails(int id) {
        Pet pet = petRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));

        String categoryName = (pet.getCategory() != null)
                ? pet.getCategory().getName()
                : null;

        return new PetDetailsDTO(
                pet.getPetId(),
                pet.getName(),
                pet.getBreed(),
                pet.getAge(),
                pet.getPrice(),
                pet.getDescription(),
                pet.getImageUrl(),
                categoryName
        );
    }

    // ── Helper ────────────────────────────────────────────────────────────
    public PetDTO convertToDTO(Pet pet) {
        return new PetDTO(
                pet.getPetId(),
                pet.getName(),
                pet.getBreed(),
                pet.getAge(),
                pet.getPrice()
        );
    }
}