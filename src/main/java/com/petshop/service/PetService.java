package com.petshop.service;

import com.petshop.dto.PetDTO;
import com.petshop.dto.PetDetailsDTO;
import com.petshop.entity.Pet;
import com.petshop.exception.ResourceNotFoundException;
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
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found with id: " + id));
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
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found with id: " + id));

        existing.setName(updatedPet.getName());
        existing.setBreed(updatedPet.getBreed());
        existing.setAge(updatedPet.getAge());
        existing.setPrice(updatedPet.getPrice());
        existing.setDescription(updatedPet.getDescription());
        existing.setImageUrl(updatedPet.getImageUrl());
        existing.setCategory(updatedPet.getCategory());

        return petRepo.save(existing);
    }

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
}