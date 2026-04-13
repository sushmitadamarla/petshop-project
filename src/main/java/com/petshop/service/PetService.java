package com.petshop.service;

import com.petshop.dto.PetDTO;
import com.petshop.entity.Pet;
import com.petshop.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepo;

    public Pet addPet(Pet pet) {
        return petRepo.save(pet);
    }

    public List<PetDTO> getAllPets() {
        return petRepo.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Pet getPetById(int id) {
        return petRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
    }
    public List<Pet> getPetsByCategory(int categoryId) {
        return petRepo.findByCategory_CategoryId(categoryId);
    }

    public void deletePet(int id) {
        petRepo.deleteById(id);
    }

    public PetDTO convertToDTO(Pet pet) {
        return new PetDTO(
                pet.getPetId(),
                pet.getName(),
                pet.getBreed(),
                pet.getAge(),
                pet.getPrice()
        );
    }

    public Pet updatePet(int id, Pet updatedPet) {
        Pet existing = petRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        existing.setName(updatedPet.getName());
        existing.setBreed(updatedPet.getBreed());
        existing.setAge(updatedPet.getAge());
        existing.setPrice(updatedPet.getPrice());
        existing.setDescription(updatedPet.getDescription());
        existing.setImageUrl(updatedPet.getImageUrl());
        existing.setCategory(updatedPet.getCategory());

        return petRepo.save(existing);
    }
}